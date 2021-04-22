package com.hdrescuer.hdrescuer.db.entity;

import androidx.room.Entity;

import java.time.Instant;

@Entity(tableName = "HEALTHBOARD")
public class HealthBoardEntity {

    public int id_session_local;
    public Instant timestamp;
    public int ehb_bpm;
    public int ehb_ox_blood;
    public int ehb_air_flow;

    public HealthBoardEntity(int id_session_local, Instant timestamp, int ehb_bpm, int ehb_ox_blood, int ehb_air_flow) {
        this.id_session_local = id_session_local;
        this.timestamp = timestamp;
        this.ehb_bpm = ehb_bpm;
        this.ehb_ox_blood = ehb_ox_blood;
        this.ehb_air_flow = ehb_air_flow;
    }

    public int getId_session_local() {
        return id_session_local;
    }

    public void setId_session_local(int id_session_local) {
        this.id_session_local = id_session_local;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
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
