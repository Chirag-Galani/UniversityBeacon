
package beacon.university.com.universitybeacon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

//@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ClassDetails {

    @SerializedName("class_name")
    private String mClassName;
    @SerializedName("department_id")
    private String mDepartmentId;

    @JsonProperty("class_name")
    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String class_name) {
        mClassName = class_name;
    }

    @JsonProperty("department_id")
    public String getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(String department_id) {
        mDepartmentId = department_id;
    }

}
