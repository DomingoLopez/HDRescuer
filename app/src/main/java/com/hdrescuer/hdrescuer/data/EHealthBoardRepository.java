package com.hdrescuer.hdrescuer.data;

import com.empatica.empalink.delegate.EmpaDataDelegate;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class EHealthBoardRepository {


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


    public EHealthBoardRepository() {
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