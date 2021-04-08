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


public class E4BandRepository implements EmpaDataDelegate {


    private final static double timezoneOffset = TimeZone.getDefault().getRawOffset() / 1000d;


    private Float battery;
    private Double tag;
    private Integer currentAccX;
    private Integer currentAccY;
    private Integer currentAccZ;
    private Float currentBvp;
    private Float currentHr;
    private Float currentGsr;
    private Float currentIbi;
    private Float currentTemp;



    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public E4BandRepository() {
        super();

        battery = 0.0f;
        currentAccX = 0;
        currentAccY = 0;
        currentAccZ = 0;
        currentBvp = 0.0f;
        currentHr = 0.0f;
        currentGsr = 0.0f;
        currentIbi = 0.0f;
        currentTemp = 0.0f;
        tag = 0.0;
    }



    @Override
    public void didReceiveBatteryLevel(float battery, double timestamp) {
        this.battery = battery;
    }

    @Override
    public void didReceiveAcceleration(int x, int y, int z, double timestamp) {
        currentAccX = x;
        currentAccY = y;
        currentAccZ = z;
    }

    @Override
    public void didReceiveBVP(float bvp, double timestamp) {
        currentBvp = bvp;

    }

    @Override
    public void didReceiveGSR(float gsr, double timestamp) {
        currentGsr = gsr;

    }

    @Override
    public void didReceiveIBI(float ibi, double timestamp) {



            // Escribir el HR obtenido del IBI cada segundo
            // todo: should be calculated from BVP
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {
                                if (averageHr != 0) {
                                    currentHr = averageHr;
                                }
                        }
                    }, 0, 1000, TimeUnit.MILLISECONDS);



        final float hr = 60.0f / ibi;
        averageHr = (float) (averageHr * 0.8 + hr * 0.2);


        currentIbi = ibi;
    }

    @Override
    public void didReceiveTemperature(float temp, double timestamp) {
        currentTemp = temp;
    }

    @Override
    public void didReceiveTag(double timestamp) {
        tag = timestamp;
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



    public static double getTimezoneOffset() {
        return timezoneOffset;
    }


    public Float getBattery() {
        return battery;
    }

    public Double getTag() {
        return tag;
    }

    public Integer getCurrentAccX() {
        return currentAccX;
    }

    public Integer getCurrentAccY() {
        return currentAccY;
    }

    public Integer getCurrentAccZ() {
        return currentAccZ;
    }

    public Float getCurrentBvp() {
        return currentBvp;
    }

    public Float getCurrentHr() {
        return currentHr;
    }

    public Float getCurrentGsr() {
        return currentGsr;
    }

    public Float getCurrentIbi() {
        return currentIbi;
    }

    public Float getCurrentTemp() {
        return currentTemp;
    }


}