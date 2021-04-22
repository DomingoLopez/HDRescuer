package com.hdrescuer.hdrescuer.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;

@Entity(tableName = "SESSION")
public class SessionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_session_local;
    public String user_id;
    public Instant timestamp_ini;
    public Instant timestamp_fin;
    public int total_time;
    public boolean e4band;
    public boolean ticwatch;
    public boolean ehealthboard;


    public SessionEntity(String user_id, Instant timestamp_ini, Instant timestamp_fin, int total_time, boolean e4band, boolean ticwatch, boolean ehealthboard) {
        this.user_id = user_id;
        this.timestamp_ini = timestamp_ini;
        this.timestamp_fin = timestamp_fin;
        this.total_time = total_time;
        this.e4band = e4band;
        this.ticwatch = ticwatch;
        this.ehealthboard = ehealthboard;
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

    public Instant getTimestamp_ini() {
        return timestamp_ini;
    }

    public void setTimestamp_ini(Instant timestamp_ini) {
        this.timestamp_ini = timestamp_ini;
    }

    public Instant getTimestamp_fin() {
        return timestamp_fin;
    }

    public void setTimestamp_fin(Instant timestamp_fin) {
        this.timestamp_fin = timestamp_fin;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
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
}
