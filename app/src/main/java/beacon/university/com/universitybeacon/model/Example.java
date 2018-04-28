
package beacon.university.com.universitybeacon.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stud_id",
        "first_name",
        "last_name",
        "email_id",
        "department_id"
})
public class Example {

    @JsonProperty("stud_id")
    private Integer studId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email_id")
    private String emailId;
    @JsonProperty("department_id")
    private String departmentId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("stud_id")
    public Integer getStudId() {
        return studId;
    }

    @JsonProperty("stud_id")
    public void setStudId(Integer studId) {
        this.studId = studId;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("email_id")
    public String getEmailId() {
        return emailId;
    }

    @JsonProperty("email_id")
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @JsonProperty("department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    @JsonProperty("department_id")
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}