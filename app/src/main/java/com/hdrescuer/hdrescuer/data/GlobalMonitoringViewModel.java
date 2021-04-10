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


    private String user_id;

    //Atributos de los sensores del TicWatch
    private MutableLiveData<Float> hrppg;
    private MutableLiveData<Float> hb;
    private MutableLiveData<Float> hrppgraw;
    private MutableLiveData<Integer> step;
    private MutableLiveData<Integer> accx;
    private MutableLiveData<Integer> accy;
    private MutableLiveData<Integer> accz;
    private MutableLiveData<Integer> acclx;
    private MutableLiveData<Integer> accly;
    private MutableLiveData<Integer> acclz;
    private MutableLiveData<Integer> girx;
    private MutableLiveData<Integer> giry;
    private MutableLiveData<Integer> girz;

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

    public GlobalMonitoringViewModel(@NonNull Application application, String id) {
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


    public String getUserId() {
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

    public MutableLiveData<Integer> getStep() {
        return step;
    }

    public MutableLiveData<Integer> getAccx() { return accx; }

    public MutableLiveData<Integer> getAccy() {
        return accy;
    }

    public MutableLiveData<Integer> getAccz() {
        return accz;
    }

    public MutableLiveData<Integer> getAcclx() {
        return acclx;
    }

    public MutableLiveData<Integer> getAccly() {
        return accly;
    }

    public MutableLiveData<Integer> getAcclz() {
        return acclz;
    }

    public MutableLiveData<Integer> getGirx() {
        return girx;
    }

    public MutableLiveData<Integer> getGiry() {
        return giry;
    }

    public MutableLiveData<Integer> getGirz() {
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

    public void setStep(Integer step){ this.step.postValue(step);}

    public void setAccx(Integer accx) { this.accx.postValue(accx);   }

    public void setAccy(Integer accy) { this.accy.postValue(accy);   }

    public void setAccz(Integer accz) { this.accz.postValue(accz);   }

    public void setAcclx(Integer acclx) {
        this.acclx.postValue(acclx);
    }

    public void setAccly(Integer accly) {
        this.accly.postValue(accly);
    }

    public void setAcclz(Integer acclz) {
        this.acclz.postValue(acclz);
    }

    public void setGirx(Integer girx) { this.girx.postValue(girx);  }

    public void setGiry(Integer giry) {
        this.giry.postValue(giry);
    }

    public void setGirz(Integer girz) {
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