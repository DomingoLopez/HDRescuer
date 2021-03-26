package com.hdrescuer.hdrescuer.data;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

public class DataRepository {


    private Float accx;
    private Float accy;
    private Float accz;
    private Float acclx;
    private Float accly;
    private Float acclz;
    private Float girx;
    private Float giry;
    private Float girz;
    private Float hrppg;
    private Float hb;
    private Float hrppgraw;
    private Float step;

    private int stepCounter;



    //Si necesitamos calcular algo en un ratio de 1 segundo.
    //Mirar como lo he hecho en la empática Viewmodel
    private float averageHr = 0;


    public DataRepository() {
        super();


        this.accx = 0.0f;//
        this.accy = 0.0f;//
        this.accz = 0.0f;//
        this.acclx = 0.0f;//
        this.accly = 0.0f;//
        this.acclz = 0.0f;//
        this.girx = 0.0f;//
        this.giry = 0.0f;//
        this.girz = 0.0f;
        this.hrppg = 0.0f;
        this.hrppgraw = 0.0f;
        this.hb = 0.0f;
        this.step = 0.0f;
        this.stepCounter = 0;


    }



    /****************GETTERS*********************
     *
     *
     ********************************************/

    public Float getAccx() {
        return accx;
    }

    public Float getAccy() {
        return accy;
    }

    public Float getAccz() {
        return accz;
    }

    public Float getAcclx() {
        return acclx;
    }

    public Float getAccly() {
        return accly;
    }

    public Float getAcclz() {
        return acclz;
    }

    public Float getGirx() {
        return girx;
    }

    public Float getGiry() {
        return giry;
    }

    public Float getGirz() {
        return girz;
    }


    public Float getHrppg() {
        return hrppg;
    }

    public Float getHrppgraw() {
        return hrppgraw;
    }

    public Float getHb() {
        return hb;
    }

    public Float getStep() {
        return step;
    }

    public Integer getStepCounter(){ return stepCounter;}

    public float getAverageHr() {
        return averageHr;
    }


    /*********************************************
     * SETTERS
     *
     ********************************************/

    public void setAccx(Float accx) {
        this.accx = accx;
    }

    public void setAccy(Float accy) {
        this.accy = accy;
    }

    public void setAccz(Float accz) {
        this.accz = accz;
    }

    public void setAcclx(Float acclx) {
        this.acclx = acclx;
    }

    public void setAccly(Float accly) {
        this.accly = accly;
    }

    public void setAcclz(Float acclz) {
        this.acclz = acclz;
    }

    public void setGirx(Float girx) {
        this.girx = girx;
    }

    public void setGiry(Float giry) {
        this.giry = giry;
    }

    public void setGirz(Float girz) {
        this.girz = girz;
    }

    public void setHrppg(Float hrppg) {
        this.hrppg = hrppg;
    }

    public void setHrppgraw(Float hrppgraw) {
        this.hrppgraw = hrppgraw;
    }

    public void setHb(Float hb) {
        this.hb = hb;
    }

    public void setStep(Float step) { this.stepCounter += step; }
}
