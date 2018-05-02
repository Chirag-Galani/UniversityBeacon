
package beacon.university.com.universitybeacon.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

//@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Announcement {

    @SerializedName("notice")
    private String mNotice;

    @JsonProperty("notice")
    public String getNotice() {
        return mNotice;
    }

    public void setNotice(String notice) {
        mNotice = notice;
    }
}
