package gsonpath.generator.extension

import com.google.common.truth.Truth
import com.google.testing.compile.JavaFileObjects
import com.google.testing.compile.JavaSourcesSubjectFactory
import gsonpath.GsonProcessorImpl
import gsonpath.generator.GeneratorTester.assertGeneratedContent
import gsonpath.generator.TestCriteria
import org.junit.Test

class GsonSubTypeExtensionTest {
    @Test
    fun givenStringKeys_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/string_keys",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenIntegerKeys_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/integer_keys",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenBooleanKeys_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/boolean_keys",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypeGsonSubType.java",
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenStringKeysWithInterface_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/using_interface",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenStringKeysWithListField_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/using_list",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenDefaultValueAndDefaultFailureOutcome_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/default_value",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenRemoveElementFailureOutcome_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/failure_outcome_remove_element",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenFailFailureOutcome_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/failure_outcome_fail",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenStringKeysAndNonPolymorphismElements_whenProcessorExecuted_expectValidGsonTypeAdapter() {
        assertGeneratedContent(TestCriteria("generator/extension/gson_sub_type/with_other_elements",

                absoluteSourceNames = listOf(
                        "generator/standard/TestGsonTypeFactory.java",
                        "generator/extension/gson_sub_type/Type.java",
                        "generator/extension/gson_sub_type/Type1.java",
                        "generator/extension/gson_sub_type/Type2.java"),

                relativeSourceNames = listOf(
                        "TypesList.java"),

                relativeGeneratedNames = listOf(
                        "TypesList_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun givenNoKeys_whenProcessorExecuted_expectNoKeysError() {
        assertPolymorphismFailure("TypesList_NoKeys.java",
                "Gson Path: Keys must be specified for the GsonSubType")
    }

    @Test
    fun givenMultipleKeys_whenProcessorExecuted_expectMultipleKeysError() {
        assertPolymorphismFailure("TypesList_MultipleKeys.java",
                "Only one keys array (string, integer or boolean) may be specified for the GsonSubType")
    }

    @Test
    fun givenBlankFieldName_whenProcessorExecuted_expectBlankFieldNameError() {
        assertPolymorphismFailure("TypesList_BlankFieldName.java",
                "Gson Path: subTypeKey cannot be blank for GsonSubType")
    }

    @Test
    fun givenNoInheritanceLink_whenProcessorExecuted_expectBlankFieldNameError() {
        assertPolymorphismFailure("TypesList_TypeInvalidInheritance.java",
                "Gson Path: subtype java.lang.String does not inherit from generator.extension.gson_sub_type.Type")
    }

    private fun assertPolymorphismFailure(className: String, errorMessage: String) {
        Truth.assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(listOf(
                        JavaFileObjects.forResource("generator/standard/TestGsonTypeFactory.java"),
                        JavaFileObjects.forResource("generator/extension/gson_sub_type/failures/$className")
                ))
                .processedWith(GsonProcessorImpl())
                .failsToCompile()
                .withErrorContaining(errorMessage)
    }
}