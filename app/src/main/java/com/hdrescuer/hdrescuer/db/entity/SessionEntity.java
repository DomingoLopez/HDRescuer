package com.hdrescuer.hdrescuer.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "SESSION", primaryKeys = {"session_id"})
public class SessionEntity {

    @NonNull
    @SerializedName("session_id")
    @Expose
    public int session_id;
    @SerializedName("user_id")
    @Expose
    public int user_id;
    @SerializedName("timestamp_ini")
    @Expose
    public String timestamp_ini;
    @SerializedName("timestamp_fin")
    @Expose
    public String timestamp_fin;
    @SerializedName("total_time")
    @Expose
    public long total_time;
    @SerializedName("e4band")
    @Expose
    public boolean e4band;
    @SerializedName("ticwatch")
    @Expose
    public boolean ticwatch;
    @SerializedName("ehealthboard")
    @Expose
    public boolean ehealthboard;
    @SerializedName("description")
    @Expose
    public String description;


    public SessionEntity(int session_id, int user_id, String timestamp_ini, String timestamp_fin, long total_time, boolean e4band, boolean ticwatch, boolean ehealthboard, String description) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.timestamp_ini = timestamp_ini;
        this.timestamp_fin = timestamp_fin;
        this.total_time = total_time;
        this.e4band = e4band;
        this.ticwatch = ticwatch;
        this.ehealthboard = ehealthboard;
        this.description = description;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTimestamp_ini() {
        return timestamp_ini;
    }

    public void setTimestamp_ini(String timestamp_ini) {
        this.timestamp_ini = timestamp_ini;
    }

    public String getTimestamp_fin() {
        return timestamp_fin;
    }

    public void setTimestamp_fin(String timestamp_fin) {
        this.timestamp_fin = timestamp_fin;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public boolean isE4band() {
        return e4band;
    }

    public void setE4band(boolean e4band) {
        this.e4band = e4band;
    }

    public boolean isTicwatch() {
        return ticwatch;
    }

    public void setTicwatch(boolean ticwatch) {
        this.ticwatch = ticwatch;
    }

    public boolean isEhealthboard() {
        return ehealthboard;
    }

    public void setEhealthboard(boolean ehealthboard) {
        this.ehealthboard = ehealthboard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
