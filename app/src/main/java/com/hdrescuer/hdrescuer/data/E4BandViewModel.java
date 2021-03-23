package com.hdrescuer.hdrescuer.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class E4BandViewModel extends AndroidViewModel implements ViewModelProvider.Factory, EmpaDataDelegate {


    private int user_id ;

    private final static double timezoneOffset = TimeZone.getDefault().getRawOffset() / 1000d;


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





    private double firstIbiTimestamp;
    private boolean tempWritten;
    private boolean accWritten;
    private boolean bvpWritten;
    private boolean ibiWritten;
    private boolean gsrWritten;

    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public E4BandViewModel(@NonNull Application application, int id) {
        super(application);
        this.user_id = id;


        battery = new MutableLiveData<>();
        currentAccX = new MutableLiveData<Integer>();
        currentAccY = new MutableLiveData<Integer>();
        currentAccZ = new MutableLiveData<Integer>();
        currentBvp = new MutableLiveData<Float>();
        currentHr = new MutableLiveData<Float>();
        currentGsr = new MutableLiveData<Float>();
        currentIbi = new MutableLiveData<Float>();
        currentTemp = new MutableLiveData<Float>();
        tag = new MutableLiveData<Double>();



    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }




    @Override
    public void didReceiveBatteryLevel(float battery, double timestamp) {
        this.battery.postValue(battery);
    }

    @Override
    public void didReceiveAcceleration(int x, int y, int z, double timestamp) {
        if (!accWritten) {
            accWritten = true;
            timestamp += timezoneOffset;


        }

        currentAccX.postValue(x);
        currentAccY.postValue(y);
        currentAccZ.postValue(z);
    }

    @Override
    public void didReceiveBVP(float bvp, double timestamp) {
        if (!bvpWritten) {
            bvpWritten = true;
        }
        currentBvp.postValue(bvp);

    }

    @Override
    public void didReceiveGSR(float gsr, double timestamp) {
        if (!gsrWritten) {
            gsrWritten = true;
        }
        currentGsr.postValue(gsr);

    }

    @Override
    public void didReceiveIBI(float ibi, double timestamp) {
        timestamp += timezoneOffset;

        if (!ibiWritten) {
            ibiWritten = true;

            firstIbiTimestamp = timestamp;


            // Escribir el HR obtenido del IBI cada segundo
            // todo: should be calculated from BVP
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {
                                if (averageHr != 0) {
                                    currentHr.postValue(averageHr);
                                }
                        }
                    }, 0, 1000, TimeUnit.MILLISECONDS);

            return;
        }


        final float hr = 60.0f / ibi;
        averageHr = (float) (averageHr * 0.8 + hr * 0.2);


        currentIbi.postValue(ibi);
    }

    @Override
    public void didReceiveTemperature(float temp, double timestamp) {
        if (!tempWritten) {
            tempWritten = true;
        }

        currentTemp.postValue(temp);
    }

    @Override
    public void didReceiveTag(double timestamp) {
        timestamp += timezoneOffset;

        tag.postValue(timestamp);
    }

    //Es necesario poner este método para que no pegue fallo.
    // Esto muestra lo mal que está mantenida la librería de la Empatica
    //@Override
    public void didUpdateOnWristStatus(int status) {

    }


    /****************GETTERS*********************
     *
     *
     ********************************************/


    public int getUser_id() {
        return user_id;
    }

    public static double getTimezoneOffset() {
        return timezoneOffset;
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








}