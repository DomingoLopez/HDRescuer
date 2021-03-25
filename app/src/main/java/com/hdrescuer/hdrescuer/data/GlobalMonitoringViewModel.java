package com.hdrescuer.hdrescuer.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


public class GlobalMonitoringViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    private int user_id;
    //public GlobalMonitoringRepository globalMonitoringRepository;


    private MutableLiveData<Float> hrppg;
    private MutableLiveData<Float> hb;
    private MutableLiveData<Float> hrppgraw;
    private MutableLiveData<Float> step;

    private MutableLiveData<Float> battery;
    private MutableLiveData<Double> tag;
    private MutableLiveData<Integer> currentAccX;
    private MutableLiveData<Integer> currentAccY;
    private MutableLiveData<Integer> currentAccZ;
    private MutableLiveData<Float> currentBvp;
    private MutableLiveData<Float> currentHr;
    private MutableLiveData<Float> currentGsr;
    private MutableLiveData<Float> currentIbi;
    private MutableLiveData<Float> currentTemp;

    public GlobalMonitoringViewModel(@NonNull Application application, int id) {
        super(application);
        this.user_id = id;
        //Obtenemos la instancia singleton del globalMonitoringRepository
        //this.globalMonitoringRepository = GlobalMonitoringRepository.getInstance(id);

        this.battery = new MutableLiveData<>();
        this.tag = new MutableLiveData<>();
        this.currentAccX = new MutableLiveData<>();
        this.currentAccY = new MutableLiveData<>();
        this.currentAccZ = new MutableLiveData<>();
        this.currentBvp = new MutableLiveData<>();
        this.currentGsr = new MutableLiveData<>();
        this.currentHr = new MutableLiveData<>();
        this.currentIbi = new MutableLiveData<>();
        this.currentTemp = new MutableLiveData<>();
        this.hrppg = new MutableLiveData<>();
        this.hb = new MutableLiveData<>();
        this.hrppgraw = new MutableLiveData<>();
        this.step = new MutableLiveData<>();

    }


    public int getUserId() {
        return user_id;
    }

    public MutableLiveData<Float> getHrppg() {
        return hrppg;
    }

    public MutableLiveData<Float> getHb() {
        return hb;
    }

    public MutableLiveData<Float> getHrppgraw() {
        return hrppgraw;
    }

    public MutableLiveData<Float> getStep() {
        return step;
    }

    public MutableLiveData<Float> getBattery() {
        return battery;
    }

    public MutableLiveData<Double> getTag() {
        return tag;
    }

    public MutableLiveData<Integer> getCurrentAccX() {
        return currentAccX;
    }

    public MutableLiveData<Integer> getCurrentAccY() {
        return currentAccY;
    }

    public MutableLiveData<Integer> getCurrentAccZ() {
        return currentAccZ;
    }

    public MutableLiveData<Float> getCurrentBvp() {
        return currentBvp;
    }

    public MutableLiveData<Float> getCurrentHr() {
        return currentHr;
    }

    public MutableLiveData<Float> getCurrentGsr() {
        return currentGsr;
    }

    public MutableLiveData<Float> getCurrentIbi() {
        return currentIbi;
    }

    public MutableLiveData<Float> getCurrentTemp() {
        return currentTemp;
    }

    public void setHrppg(Float hrppg) {
        this.hrppg.postValue(hrppg);
    }

    public void setHb(Float hb) {
        this.hb.postValue(hb);
    }

    public void setHrppgraw(Float hrppgraw) {
        this.hrppgraw.postValue(hrppgraw);
    }

    public void setStep(Float step) {
        this.step.postValue(step);
    }

    public void setBattery(Float battery) {
        this.battery.postValue(battery);
    }

    public void setTag(Double tag) {
        this.tag.postValue(tag);
    }

    public void setCurrentAccX(Integer currentAccX) {
        this.currentAccX.postValue(currentAccX);
    }

    public void setCurrentAccY(Integer currentAccY) {
        this.currentAccY.postValue(currentAccY);
    }

    public void setCurrentAccZ(Integer currentAccZ) {
        this.currentAccZ.postValue(currentAccZ);
    }

    public void setCurrentBvp(Float currentBvp) {
        this.currentBvp.postValue(currentBvp);
    }

    public void setCurrentHr(Float currentHr) {
        this.currentHr.postValue(currentHr);
    }

    public void setCurrentGsr(Float currentGsr) {
        this.currentGsr.postValue(currentGsr);
    }

    public void setCurrentIbi(Float currentIbi) {
        this.currentIbi.postValue(currentIbi);
    }

    public void setCurrentTemp(Float currentTemp) {
        this.currentTemp.postValue(currentTemp);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}