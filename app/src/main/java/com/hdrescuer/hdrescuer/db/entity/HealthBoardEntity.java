package com.hdrescuer.hdrescuer.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "HEALTHBOARD", primaryKeys = {"session_id","timestamp"})
public class HealthBoardEntity {

    @NonNull
    public int session_id;
    @NonNull
    public String timestamp;

    public int ehb_bpm;
    public int ehb_ox_blood;
    public int ehb_air_flow;

    public HealthBoardEntity(int session_id, String timestamp, int ehb_bpm, int ehb_ox_blood, int ehb_air_flow) {
        this.session_id = session_id;
        this.timestamp = timestamp;
        this.ehb_bpm = ehb_bpm;
        this.ehb_ox_blood = ehb_ox_blood;
        this.ehb_air_flow = ehb_air_flow;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getEhb_bpm() {
        return ehb_bpm;
    }

    public void setEhb_bpm(int ehb_bpm) {
        this.ehb_bpm = ehb_bpm;
    }

    public int getEhb_ox_blood() {
        return ehb_ox_blood;
    }

    public void setEhb_ox_blood(int ehb_ox_blood) {
        this.ehb_ox_blood = ehb_ox_blood;
    }

    public int getEhb_air_flow() {
        return ehb_air_flow;
    }

    public void setEhb_air_flow(int ehb_air_flow) {
        this.ehb_air_flow = ehb_air_flow;
    }
}
