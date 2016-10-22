package adapter.auto.interface_example.flatten_json;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public final class TestFlattenJsonWithInterface_GsonPathModel implements TestFlattenJsonWithInterface {
    private final String flattenExample;

    public TestFlattenJsonWithInterface_GsonPathModel(String flattenExample) {
        this.flattenExample = flattenExample;
    }

    @Override
    public String getFlattenExample() {
        return flattenExample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestFlattenJsonWithInterface_GsonPathModel that = (TestFlattenJsonWithInterface_GsonPathModel) o;

        if (flattenExample != null ? !flattenExample.equals(that.flattenExample) : that.flattenExample != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = flattenExample != null ? flattenExample.hashCode() : 0;
        return result;
    }
}