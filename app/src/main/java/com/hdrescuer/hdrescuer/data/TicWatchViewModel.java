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


public class TicWatchViewModel extends AndroidViewModel implements ViewModelProvider.Factory{


    private int user_id ;


    private MutableLiveData<Float> accx;
    private MutableLiveData<Float> accy;
    private MutableLiveData<Float> accz;
    private MutableLiveData<Float> acclx;
    private MutableLiveData<Float> accly;
    private MutableLiveData<Float> acclz;
    private MutableLiveData<Float> girx;
    private MutableLiveData<Float> giry;
    private MutableLiveData<Float> girz;
    private MutableLiveData<Float> hrppg;
    private MutableLiveData<Float> hrppgraw;
    private MutableLiveData<Float> step;




    //Si necesitamos calcular algo en un ratio de 1 segundo.
    //Mirar como lo he hecho en la empática Viewmodel
    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public TicWatchViewModel(@NonNull Application application, int id) {
        super(application);
        this.user_id = id;

        this.accx = new MutableLiveData<>();
        this.accy = new MutableLiveData<>();
        this.accz = new MutableLiveData<>();
        this.acclx = new MutableLiveData<>();
        this.accly = new MutableLiveData<>();
        this.acclz = new MutableLiveData<>();
        this.girx = new MutableLiveData<>();
        this.giry = new MutableLiveData<>();
        this.girz = new MutableLiveData<>();
        this.hrppg = new MutableLiveData<>();
        this.hrppgraw = new MutableLiveData<>();
        this.step = new MutableLiveData<>();


    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }




    /****************GETTERS*********************
     *
     *
     ********************************************/
    public int getUser_id() {
        return user_id;
    }

    public MutableLiveData<Float> getAccx() {
        return accx;
    }

    public MutableLiveData<Float> getAccy() {
        return accy;
    }

    public MutableLiveData<Float> getAccz() {
        return accz;
    }

    public MutableLiveData<Float> getAcclx() {
        return acclx;
    }

    public MutableLiveData<Float> getAccly() {
        return accly;
    }

    public MutableLiveData<Float> getAcclz() {
        return acclz;
    }

    public MutableLiveData<Float> getGirx() {
        return girx;
    }

    public MutableLiveData<Float> getGiry() {
        return giry;
    }

    public MutableLiveData<Float> getGirz() {
        return girz;
    }

    public MutableLiveData<Float> getHrppg() {
        return hrppg;
    }

    public MutableLiveData<Float> getHrppgraw() {
        return hrppgraw;
    }

    public MutableLiveData<Float> getStep() {
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
        this.accx.postValue(accx);
    }

    public void setAccy(Float accy) {
        this.accy.postValue(accy);
    }

    public void setAccz(Float accz) {
        this.accz.postValue(accz);
    }

    public void setAcclx(Float acclx) {
        this.acclx.postValue(acclx);
    }

    public void setAccly(Float accly) {
        this.accly.postValue(accly);
    }

    public void setAcclz(Float acclz) {
        this.acclz.postValue(acclz);
    }

    public void setGirx(Float girx) {
        this.girx.postValue(girx);
    }

    public void setGiry(Float giry) {
        this.giry.postValue(giry);
    }

    public void setGirz(Float girz) {
        this.girz.postValue(girz);
    }

    public void setHrppg(Float hrppg) {
        this.hrppg.postValue(hrppg);
    }

    public void setHrppgraw(Float hrppgraw) {
        this.hrppgraw.postValue(hrppgraw);
    }

    public void setStep(Float step) {
        this.step.postValue(step);
    }
}
