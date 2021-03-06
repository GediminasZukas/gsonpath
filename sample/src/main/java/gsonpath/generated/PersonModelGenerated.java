package gsonpath.generated;

import com.google.gson.annotations.SerializedName;
import gsonpath.annotation.AutoGsonAdapter;

@AutoGsonAdapter
public interface PersonModelGenerated {
    PersonModel[] getPeople();

    @AutoGsonAdapter(rootField = "person.names")
    interface PersonModel {
        @SerializedName("first")
        String getFirstName();
    }

}
