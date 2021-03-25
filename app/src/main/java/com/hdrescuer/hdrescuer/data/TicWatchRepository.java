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


    private int user_id ;


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
    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public TicWatchRepository() {
        super();

        this.stepCounter = 0;

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

    public void setStep(Float step) {
        //OJO. vamos a probar a que sume los pasos
        this.step = step;
    }
}
