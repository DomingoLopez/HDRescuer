package com.hdrescuer.hdrescuer.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;

@Entity(tableName = "SESSION", primaryKeys = {"id_session_local"})
public class SessionEntity {

    @NonNull
    public int id_session_local;

    public String user_id;
    public String timestamp_ini;
    public String timestamp_fin;
    public long total_time;
    public boolean e4band;
    public boolean ticwatch;
    public boolean ehealthboard;
    public String description;


    public SessionEntity(int id_session_local, String user_id, String timestamp_ini, String timestamp_fin, long total_time, boolean e4band, boolean ticwatch, boolean ehealthboard, String description) {
        this.id_session_local = id_session_local;
        this.user_id = user_id;
        this.timestamp_ini = timestamp_ini;
        this.timestamp_fin = timestamp_fin;
        this.total_time = total_time;
        this.e4band = e4band;
        this.ticwatch = ticwatch;
        this.ehealthboard = ehealthboard;
        this.description = description;
    }

    public int getId_session_local() {
        return id_session_local;
    }

    public void setId_session_local(int id_session_local) {
        this.id_session_local = id_session_local;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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
