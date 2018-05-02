
package beacon.university.com.universitybeacon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ClassSchedule {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("lec_date")
    private String mLecDate;

    @JsonProperty("description")
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @JsonProperty("lec_date")
    public String getLecDate() {
        return mLecDate;
    }

    public void setLecDate(String lecDate) {
        mLecDate = lecDate;
    }

}
