
package com.hdrescuer.hdrescuer.retrofit.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session implements Serializable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("timestamp_ini")
    @Expose
    private String timestampIni;
    @SerializedName("timestamp_fin")
    @Expose
    private String timestampFin;
    @SerializedName("total_time")
    @Expose
    private Integer totalTime;
    @SerializedName("e4band")
    @Expose
    private Boolean e4band;
    @SerializedName("ticwatch")
    @Expose
    private Boolean ticwatch;
    @SerializedName("ehealthboard")
    @Expose
    private Boolean ehealthboard;
    private final static long serialVersionUID = 1292988452008497775L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Session() {
    }

    /**
     * 
     * @param timestampFin
     * @param e4band
     * @param ticwatch
     * @param totalTime
     * @param timestampIni
     * @param ehealthboard
     * @param userId
     */
    public Session(String userId, String timestampIni, String timestampFin, Integer totalTime, Boolean e4band, Boolean ticwatch, Boolean ehealthboard) {
        super();
        this.userId = userId;
        this.timestampIni = timestampIni;
        this.timestampFin = timestampFin;
        this.totalTime = totalTime;
        this.e4band = e4band;
        this.ticwatch = ticwatch;
        this.ehealthboard = ehealthboard;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestampIni() {
        return timestampIni;
    }

    public void setTimestampIni(String timestampIni) {
        this.timestampIni = timestampIni;
    }

    public String getTimestampFin() {
        return timestampFin;
    }

    public void setTimestampFin(String timestampFin) {
        this.timestampFin = timestampFin;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getE4band() {
        return e4band;
    }

    public void setE4band(Boolean e4band) {
        this.e4band = e4band;
    }

    public Boolean getTicwatch() {
        return ticwatch;
    }

    public void setTicwatch(Boolean ticwatch) {
        this.ticwatch = ticwatch;
    }

    public Boolean getEhealthboard() {
        return ehealthboard;
    }

    public void setEhealthboard(Boolean ehealthboard) {
        this.ehealthboard = ehealthboard;
    }

}
