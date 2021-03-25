package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.util.Log;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.data.E4BandRepository;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;
import com.hdrescuer.hdrescuer.data.TicWatchRepository;

public class SampleRateFilterThread extends Thread{

    private TicWatchRepository ticWatchRepository;
    private E4BandRepository e4BandRepository;
    private GlobalMonitoringViewModel globalMonitoringViewModel;
    long timestamp;
    public static  String STATUS="ACTIVO";

    public SampleRateFilterThread(TicWatchRepository ticWatchRepository,
                                  E4BandRepository e4BandRepository,
                                  GlobalMonitoringViewModel globalMonitoringViewModel){

        this.ticWatchRepository = ticWatchRepository;
        this.e4BandRepository = e4BandRepository;
        this.globalMonitoringViewModel = globalMonitoringViewModel;

    }



    @Override
    public void run(){
       this.timestamp = System.currentTimeMillis();

       try{

           int i = 0;
           while (STATUS.equals("ACTIVO")){
               Thread.sleep(Constants.SAMPLE_RATE);
               updateE4BandData();
               updateTicWatchData();
               Log.i("INSIDE","THREAD");

           }


       }catch (Exception e){

       }

    }

    private void updateE4BandData(){
        this.globalMonitoringViewModel.setBattery(this.e4BandRepository.getBattery());
        this.globalMonitoringViewModel.setTag(this.e4BandRepository.getTag());
        this.globalMonitoringViewModel.setCurrentAccX(this.e4BandRepository.getCurrentAccX());
        this.globalMonitoringViewModel.setCurrentAccY(this.e4BandRepository.getCurrentAccY());
        this.globalMonitoringViewModel.setCurrentAccZ(this.e4BandRepository.getCurrentAccZ());
        this.globalMonitoringViewModel.setCurrentBvp(this.e4BandRepository.getCurrentBvp());
        this.globalMonitoringViewModel.setCurrentHr(this.e4BandRepository.getCurrentHr());
        this.globalMonitoringViewModel.setCurrentGsr(this.e4BandRepository.getCurrentGsr());
        this.globalMonitoringViewModel.setCurrentIbi(this.e4BandRepository.getCurrentIbi());
        this.globalMonitoringViewModel.setCurrentTemp(this.e4BandRepository.getCurrentTemp());

    }

    private void updateTicWatchData(){

        this.globalMonitoringViewModel.setHrppg(this.ticWatchRepository.getHrppg());
        this.globalMonitoringViewModel.setHb(this.ticWatchRepository.getHb());
        this.globalMonitoringViewModel.setHrppgraw(this.ticWatchRepository.getHrppgraw());
        this.globalMonitoringViewModel.setStep(this.ticWatchRepository.getStep());
        this.globalMonitoringViewModel.setAccx(this.ticWatchRepository.getAccx());
        this.globalMonitoringViewModel.setAccy(this.ticWatchRepository.getAccy());
        this.globalMonitoringViewModel.setAccz(this.ticWatchRepository.getAccz());
        this.globalMonitoringViewModel.setAcclx(this.ticWatchRepository.getAcclx());
        this.globalMonitoringViewModel.setAccly(this.ticWatchRepository.getAccly());
        this.globalMonitoringViewModel.setAcclz(this.ticWatchRepository.getAcclz());
        this.globalMonitoringViewModel.setGirx(this.ticWatchRepository.getGirx());
        this.globalMonitoringViewModel.setGiry(this.ticWatchRepository.getGiry());
        this.globalMonitoringViewModel.setGirz(this.ticWatchRepository.getGirz());

    }


}
