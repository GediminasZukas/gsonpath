package gsonpath.adapter.standard.adapter

import com.squareup.javapoet.ClassName
import gsonpath.LazyFactoryMetadata
import gsonpath.adapter.standard.adapter.properties.AutoGsonAdapterPropertiesFactory
import gsonpath.adapter.standard.adapter.read.ReadParams
import gsonpath.adapter.standard.adapter.write.WriteParams
import gsonpath.adapter.standard.interf.ModelInterfaceGenerator
import gsonpath.adapter.standard.model.*
import gsonpath.annotation.AutoGsonAdapter
import gsonpath.compiler.generateClassName
import gsonpath.model.FieldInfo
import gsonpath.util.TypeHandler
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ExecutableType

class AdapterModelMetadataFactory(
        private val fieldInfoFactory: FieldInfoFactory,
        private val gsonObjectTreeFactory: GsonObjectTreeFactory,
        private val typeHandler: TypeHandler,
        private val modelInterfaceGenerator: ModelInterfaceGenerator,
        private val autoGsonAdapterPropertiesFactory: AutoGsonAdapterPropertiesFactory) {

    fun createMetadata(
            modelElement: TypeElement,
            autoGsonAnnotation: AutoGsonAdapter,
            lazyFactoryMetadata: LazyFactoryMetadata): AdapterModelMetadata {

        val modelClassName = ClassName.get(modelElement)
        val adapterClassName = ClassName.get(modelClassName.packageName(),
                generateClassName(modelClassName, "GsonTypeAdapter"))

        val concreteClassName: ClassName
        val fieldInfoList: List<FieldInfo>
        val isModelInterface = modelElement.kind.isInterface

        val properties = autoGsonAdapterPropertiesFactory.create(
                modelElement, autoGsonAnnotation, lazyFactoryMetadata, isModelInterface)

        val requiresConstructorInjection: Boolean =
                if (isModelInterface) {
                    true
                } else {
                    findNonEmptyConstructor(modelElement) != null
                }

        val adapterGenericTypeClassNames: List<ClassName>

        if (!isModelInterface) {
            concreteClassName = modelClassName
            adapterGenericTypeClassNames = listOf(modelClassName)

            fieldInfoList = fieldInfoFactory.getModelFieldsFromElement(
                    modelElement,
                    properties.fieldsRequireAnnotation,
                    requiresConstructorInjection)

        } else {
            val interfaceInfo = modelInterfaceGenerator.handle(modelElement)
            concreteClassName = interfaceInfo.parentClassName
            adapterGenericTypeClassNames = listOf(interfaceInfo.parentClassName, modelClassName)

            fieldInfoList = fieldInfoFactory.getModelFieldsFromInterface(interfaceInfo)
        }

        val gsonTreeResult = gsonObjectTreeFactory
                .createGsonObject(fieldInfoList, properties.rootField,
                        GsonObjectMetadata(properties.flattenDelimiter,
                                properties.gsonFieldNamingPolicy,
                                properties.gsonFieldValidationType,
                                properties.pathSubstitutions))

        val rootObject = gsonTreeResult.rootObject
        val flattenedFields = gsonTreeResult.flattenedFields
        val mandatoryFields = MandatoryFieldInfoFactory().createMandatoryFieldsFromGsonObject(rootObject)

        val readParams = ReadParams(
                baseElement = modelClassName,
                concreteElement = concreteClassName,
                requiresConstructorInjection = requiresConstructorInjection,
                mandatoryFields = mandatoryFields,
                rootElements = rootObject,
                flattenedFields = flattenedFields,
                objectIndexes = GsonModelIndexAssigner.assignObjectIndexes(rootObject),
                arrayIndexes = GsonModelIndexAssigner.assignArrayIndexes(rootObject))

        val writeParams = WriteParams(
                elementClassName = modelClassName,
                rootElements = rootObject,
                serializeNulls = properties.serializeNulls)

        return AdapterModelMetadata(
                modelClassName,
                adapterGenericTypeClassNames,
                adapterClassName,
                isModelInterface,
                rootObject,
                readParams,
                writeParams)
    }

    /**
     * Finds a constructor within the input [TypeElement] that has at least one argument.
     *
     * @param modelElement the model being searched.
     */
    private fun findNonEmptyConstructor(modelElement: TypeElement): ExecutableType? {
        return typeHandler.getAllMembers(modelElement)
                .asSequence()
                .filter { it.kind == ElementKind.CONSTRUCTOR }
                .map { (it.asType() as ExecutableType) }
                .find { it.parameterTypes.size > 0 }
    }
}