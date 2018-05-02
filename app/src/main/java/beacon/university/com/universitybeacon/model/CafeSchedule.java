
package beacon.university.com.universitybeacon.model;

//import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

//@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CafeSchedule {

    @SerializedName("day_string")
    private String mDayString;
    @SerializedName("timings")
    private String mTimings;

    public CafeSchedule() {
    }

    public CafeSchedule(String mDayString, String mTimings) {

        this.mDayString = mDayString;
        this.mTimings = mTimings;
    }

    @JsonProperty("day_string")
    public String getDayString() {
        return mDayString;
    }

    public void setDayString(String dayString) {
        mDayString = dayString;
    }

    @JsonProperty("timings")
    public String getTimings() {
        return mTimings;
    }

    public void setTimings(String timings) {
        mTimings = timings;
    }

}
