package com.hdrescuer.hdrescuer.data;

import com.empatica.empalink.delegate.EmpaDataDelegate;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class EHealthBoardRepository {


    private Integer BMP;
    private Integer OxBlood;
    private Integer airFlow;


    public EHealthBoardRepository() {
        super();

       this.BMP = 0;
       this.OxBlood = 0;
       this.airFlow = 0;
    }

    public Integer getBMP() {
        return BMP;
    }

    public void setBMP(Integer BMP) {
        this.BMP = BMP;
    }

    public Integer getOxBlood() {
        return OxBlood;
    }

    public void setOxBlood(Integer oxBlood) {
        OxBlood = oxBlood;
    }

    public Integer getAirFlow(){return this.airFlow; }

    public void setAirFlow(Integer air){this.airFlow = air;}




    public void reset(){
        this.BMP = 0;
        this.OxBlood = 0;
        this.airFlow = 0;
    }
}