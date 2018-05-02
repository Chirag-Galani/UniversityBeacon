package beacon.university.com.universitybeacon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by satendra on 4/28/2018.
 */


public class CafeMenu {

    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("menu")
    @Expose
    private String menu;

    public void CafeMenu(String station, String menu) {
        this.station = station;
        this.menu = menu;
    }

    @JsonProperty("station")
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @JsonProperty("menu")
    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [station = "+station+", menu = "+menu+"]";
    }
}
