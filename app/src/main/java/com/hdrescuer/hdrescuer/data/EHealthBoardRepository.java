package com.hdrescuer.hdrescuer.data;

import com.empatica.empalink.delegate.EmpaDataDelegate;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class EHealthBoardRepository {


    private Integer BMP;
    private Integer OxBlood;



    public EHealthBoardRepository() {
        super();

       this.BMP = 0;
       this.OxBlood = 0;
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
}