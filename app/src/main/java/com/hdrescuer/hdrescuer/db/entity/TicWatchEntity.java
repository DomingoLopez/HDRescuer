package com.hdrescuer.hdrescuer.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "TICWATCH", primaryKeys = {"session_id","timestamp"})
public class TicWatchEntity {

    @NonNull
    public int session_id;
    @NonNull
    public String timestamp;

    public int tic_accx;
    public int tic_accy;
    public int tic_accz;
    public int tic_acclx;
    public int tic_accly;
    public int tic_acclz;
    public int tic_girx;
    public int tic_giry;
    public int tic_girz;
    public float tic_hrppg;
    public int tic_step;


    public TicWatchEntity(int session_id, String timestamp, int tic_accx, int tic_accy, int tic_accz, int tic_acclx, int tic_accly, int tic_acclz, int tic_girx, int tic_giry, int tic_girz, float tic_hrppg, int tic_step) {
        this.session_id = session_id;
        this.timestamp = timestamp;
        this.tic_accx = tic_accx;
        this.tic_accy = tic_accy;
        this.tic_accz = tic_accz;
        this.tic_acclx = tic_acclx;
        this.tic_accly = tic_accly;
        this.tic_acclz = tic_acclz;
        this.tic_girx = tic_girx;
        this.tic_giry = tic_giry;
        this.tic_girz = tic_girz;
        this.tic_hrppg = tic_hrppg;
        this.tic_step = tic_step;
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

    public int getTic_accx() {
        return tic_accx;
    }

    public void setTic_accx(int tic_accx) {
        this.tic_accx = tic_accx;
    }

    public int getTic_accy() {
        return tic_accy;
    }

    public void setTic_accy(int tic_accy) {
        this.tic_accy = tic_accy;
    }

    public int getTic_accz() {
        return tic_accz;
    }

    public void setTic_accz(int tic_accz) {
        this.tic_accz = tic_accz;
    }

    public int getTic_acclx() {
        return tic_acclx;
    }

    public void setTic_acclx(int tic_acclx) {
        this.tic_acclx = tic_acclx;
    }

    public int getTic_accly() {
        return tic_accly;
    }

    public void setTic_accly(int tic_accly) {
        this.tic_accly = tic_accly;
    }

    public int getTic_acclz() {
        return tic_acclz;
    }

    public void setTic_acclz(int tic_acclz) {
        this.tic_acclz = tic_acclz;
    }

    public int getTic_girx() {
        return tic_girx;
    }

    public void setTic_girx(int tic_girx) {
        this.tic_girx = tic_girx;
    }

    public int getTic_giry() {
        return tic_giry;
    }

    public void setTic_giry(int tic_giry) {
        this.tic_giry = tic_giry;
    }

    public int getTic_girz() {
        return tic_girz;
    }

    public void setTic_girz(int tic_girz) {
        this.tic_girz = tic_girz;
    }

    public float getTic_hrppg() {
        return tic_hrppg;
    }

    public void setTic_hrppg(float tic_hrppg) {
        this.tic_hrppg = tic_hrppg;
    }

    public int getTic_step() {
        return tic_step;
    }

    public void setTic_step(int tic_step) {
        this.tic_step = tic_step;
    }
}
