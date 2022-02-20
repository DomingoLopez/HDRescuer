package com.hdrescuer.hdrescuer.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "EMPATICA", primaryKeys = {"session_id","timestamp"})
public class EmpaticaEntity {

    @NonNull
    public int session_id;
    @NonNull
    public String timestamp;

    public int e4_accx;
    public int e4_accy;
    public int e4_accz;
    public float e4_bvp;
    public int e4_hr;
    public float e4_gsr;
    public float e4_ibi;
    public float e4_temp;


    public EmpaticaEntity(int session_id, String timestamp, int e4_accx, int e4_accy, int e4_accz, float e4_bvp, int e4_hr, float e4_gsr, float e4_ibi, float e4_temp) {
        this.session_id = session_id;
        this.timestamp = timestamp;
        this.e4_accx = e4_accx;
        this.e4_accy = e4_accy;
        this.e4_accz = e4_accz;
        this.e4_bvp = e4_bvp;
        this.e4_hr = e4_hr;
        this.e4_gsr = e4_gsr;
        this.e4_ibi = e4_ibi;
        this.e4_temp = e4_temp;
    }


    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

    public int getE4_accx() {
        return e4_accx;
    }

    public void setE4_accx(int e4_accx) {
        this.e4_accx = e4_accx;
    }

    public int getE4_accy() {
        return e4_accy;
    }

    public void setE4_accy(int e4_accy) {
        this.e4_accy = e4_accy;
    }

    public int getE4_accz() {
        return e4_accz;
    }

    public void setE4_accz(int e4_accz) {
        this.e4_accz = e4_accz;
    }

    public float getE4_bvp() {
        return e4_bvp;
    }

    public void setE4_bvp(float e4_bvp) {
        this.e4_bvp = e4_bvp;
    }

    public int getE4_hr() {
        return e4_hr;
    }

    public void setE4_hr(int e4_hr) {
        this.e4_hr = e4_hr;
    }

    public float getE4_gsr() {
        return e4_gsr;
    }

    public void setE4_gsr(float e4_gsr) {
        this.e4_gsr = e4_gsr;
    }

    public float getE4_ibi() {
        return e4_ibi;
    }

    public void setE4_ibi(float e4_ibi) {
        this.e4_ibi = e4_ibi;
    }

    public float getE4_temp() {
        return e4_temp;
    }

    public void setE4_temp(float e4_temp) {
        this.e4_temp = e4_temp;
    }
}
