package com.hdrescuer.hdrescuer.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.text.DecimalFormat;


public class GlobalMonitoringViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    private int user_id;

    //Atributos de los sensores del TicWatch
    private MutableLiveData<Float> hrppg;
    private MutableLiveData<Float> hb;
    private MutableLiveData<Float> hrppgraw;
    private MutableLiveData<Float> step;
    private MutableLiveData<Float> accx;
    private MutableLiveData<Float> accy;
    private MutableLiveData<Float> accz;
    private MutableLiveData<Float> acclx;
    private MutableLiveData<Float> accly;
    private MutableLiveData<Float> acclz;
    private MutableLiveData<Float> girx;
    private MutableLiveData<Float> giry;
    private MutableLiveData<Float> girz;

    //Atributos de los sensores de la Empática
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

        //Iniciamos las mutablelivedata para la empática
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


        //Iniciamos las mutablelivedata para el reloj
        this.hrppg = new MutableLiveData<>();
        this.hb = new MutableLiveData<>();
        this.hrppgraw = new MutableLiveData<>();
        this.step = new MutableLiveData<>();
        this.accx = new MutableLiveData<>();
        this.accy = new MutableLiveData<>();
        this.accz = new MutableLiveData<>();
        this.acclx = new MutableLiveData<>();
        this.accly = new MutableLiveData<>();
        this.acclz = new MutableLiveData<>();
        this.girx = new MutableLiveData<>();
        this.giry = new MutableLiveData<>();
        this.girz = new MutableLiveData<>();

    }


    public int getUserId() {
        return user_id;
    }

    //GETTERS DEL TICWATCH
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

    public MutableLiveData<Float> getAccx() { return accx; }

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






    //GETTERS DE LA EMPÁTICA

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






    //METODOS SETTER DEL TICWATCH
    public void setHrppg(Float hrppg) {
        this.hrppg.postValue(hrppg);
    }

    public void setHb(Float hb) {
        this.hb.postValue(hb);
    }

    public void setHrppgraw(Float hrppgraw) {
        this.hrppgraw.postValue(hrppgraw);
    }

    public void setStep(Float step){ this.step.postValue(step);}

    public void setAccx(Float accx) { this.accx.postValue(accx);   }

    public void setAccy(Float accy) { this.accy.postValue(accy);   }

    public void setAccz(Float accz) { this.accz.postValue(accz);   }

    public void setAcclx(Float acclx) {
        this.acclx.postValue(acclx);
    }

    public void setAccly(Float accly) {
        this.accly.postValue(accly);
    }

    public void setAcclz(Float acclz) {
        this.acclz.postValue(acclz);
    }

    public void setGirx(Float girx) { this.girx.postValue(girx);  }

    public void setGiry(Float giry) {
        this.giry.postValue(giry);
    }

    public void setGirz(Float girz) {
        this.girz.postValue(girz);
    }


    //Métodos SETTER DE LA EMPATICA
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