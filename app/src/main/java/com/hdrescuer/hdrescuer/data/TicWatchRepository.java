package com.hdrescuer.hdrescuer.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TicWatchRepository {



    private Integer accx;
    private Integer accy;
    private Integer accz;
    private Integer acclx;
    private Integer accly;
    private Integer acclz;
    private Integer girx;
    private Integer giry;
    private Integer girz;
    private Float hrppg;
    private Float hb;
    private Float hrppgraw;
    private Integer step;
    private int stepCounter;




    //Si necesitamos calcular algo en un ratio de 1 segundo.
    //Mirar como lo he hecho en la empática Viewmodel
    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public TicWatchRepository() {
        super();

        this.stepCounter = 0;

        this.accx = 0;
        this.accy = 0;
        this.accz = 0;
        this.acclx = 0;
        this.accly = 0;
        this.acclz = 0;
        this.girx = 0;
        this.giry = 0;
        this.girz = 0;
        this.hrppg = 0.0f;
        this.hrppgraw = 0.0f;
        this.hb = 0.0f;
        this.step = 0;


    }



    /****************GETTERS*********************
     *
     *
     ********************************************/

    public Integer getAccx() {
        return accx;
    }

    public Integer getAccy() {
        return accy;
    }

    public Integer getAccz() {
        return accz;
    }

    public Integer getAcclx() {
        return acclx;
    }

    public Integer getAccly() {
        return accly;
    }

    public Integer getAcclz() {
        return acclz;
    }

    public Integer getGirx() {
        return girx;
    }

    public Integer getGiry() {
        return giry;
    }

    public Integer getGirz() {
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

    public Integer getStep() {
        return step;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public float getAverageHr() {
        return averageHr;
    }


    /*********************************************
     * SETTERS
     *
     ********************************************/

    public void setAccx(Float accx) { this.accx = Math.round(accx); }

    public void setAccy(Float accy) {
        this.accy = Math.round(accy);
    }

    public void setAccz(Float accz) {
        this.accz = Math.round(accz);
    }

    public void setAcclx(Float acclx) {
        this.acclx = Math.round(acclx);
    }

    public void setAccly(Float accly) {
        this.accly = Math.round(accly);
    }

    public void setAcclz(Float acclz) {
        this.acclz = Math.round(acclz);
    }

    public void setGirx(Float girx) {
        this.girx = Math.round(girx);
    }

    public void setGiry(Float giry) {
        this.giry = Math.round(giry);
    }

    public void setGirz(Float girz) {
        this.girz = Math.round(girz);
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

    public void setStep(Integer step) {
        //OJO. vamos a probar a que sume los pasos
        this.step = step;
    }



    public void reset(){
        this.stepCounter = 0;

        this.accx = 0;
        this.accy = 0;
        this.accz = 0;
        this.acclx = 0;
        this.accly = 0;
        this.acclz = 0;
        this.girx = 0;
        this.giry = 0;
        this.girz = 0;
        this.hrppg = 0.0f;
        this.hrppgraw = 0.0f;
        this.hb = 0.0f;
        this.step = 0;
    }
}
