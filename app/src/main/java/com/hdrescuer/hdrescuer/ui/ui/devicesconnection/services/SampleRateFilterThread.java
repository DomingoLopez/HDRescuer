package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.E4BandRepository;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;
import com.hdrescuer.hdrescuer.data.TicWatchRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

public class SampleRateFilterThread extends Thread{

    private TicWatchRepository ticWatchRepository;
    private E4BandRepository e4BandRepository;
    private GlobalMonitoringViewModel globalMonitoringViewModel;
    long timestamp;
    public static  String STATUS="ACTIVO";
    private static String ACTION_SEND = "ACTION_SEND";

    private String user_id;
//    private long instant = 0;
    private Instant instant;

    public SampleRateFilterThread(TicWatchRepository ticWatchRepository,
                                  E4BandRepository e4BandRepository,
                                  GlobalMonitoringViewModel globalMonitoringViewModel, String user_id){

        this.user_id = user_id;
        this.ticWatchRepository = ticWatchRepository;
        this.e4BandRepository = e4BandRepository;
        this.globalMonitoringViewModel = globalMonitoringViewModel;

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run(){
       this.timestamp = System.currentTimeMillis();

       try{

           int i = 0;
           while (STATUS.equals("ACTIVO")){
               Thread.sleep(Constants.SAMPLE_RATE);
               updateE4BandData();
               updateTicWatchData();

               //Creamos el intent para pasarlo al IntentService
               Intent restIntent = new Intent(MyApp.getContext(), RestSampleRateService.class);
               restIntent.setAction(ACTION_SEND);

               //Est lo mejor es TODO: Hacer una clase serializable y pasar el objeto entero
               //Variables a pasar del Watch
               Clock reloj = Clock.systemUTC();
               this.instant = reloj.instant();
               //Clock clock = Clock.systemUTC();
               //this.instant = clock.millis(); //TimeStamp en Milisegundos. Podemos usar el instant() de arriba y tendría un formato 2021-03-23T17:40:12.356Z por ejemplo

               restIntent.putExtra("tic_hrppg",this.ticWatchRepository.getHrppg().toString());
               restIntent.putExtra("tic_hrppgraw",this.ticWatchRepository.getHrppgraw().toString());
               restIntent.putExtra("tic_step",this.ticWatchRepository.getStep().toString());
               restIntent.putExtra("tic_accx",this.ticWatchRepository.getAccx().toString());
               restIntent.putExtra("tic_accy",this.ticWatchRepository.getAccy().toString());
               restIntent.putExtra("tic_accz",this.ticWatchRepository.getAccz().toString());
               restIntent.putExtra("tic_acclx",this.ticWatchRepository.getAcclx().toString());
               restIntent.putExtra("tic_accly",this.ticWatchRepository.getAccly().toString());
               restIntent.putExtra("tic_acclz",this.ticWatchRepository.getAcclz().toString());
               restIntent.putExtra("tic_girx",this.ticWatchRepository.getGirx().toString());
               restIntent.putExtra("tic_giry",this.ticWatchRepository.getGiry().toString());
               restIntent.putExtra("tic_girz",this.ticWatchRepository.getGirz().toString());

               restIntent.putExtra("e4_accx",this.e4BandRepository.getCurrentAccX().toString());
               restIntent.putExtra("e4_accy",this.e4BandRepository.getCurrentAccY().toString());
               restIntent.putExtra("e4_accz",this.e4BandRepository.getCurrentAccZ().toString());
               restIntent.putExtra("e4_bvp",this.e4BandRepository.getCurrentBvp().toString());
               restIntent.putExtra("e4_hr",this.e4BandRepository.getCurrentHr().toString());
               restIntent.putExtra("e4_gsr",this.e4BandRepository.getCurrentGsr().toString());
               restIntent.putExtra("e4_ibi",this.e4BandRepository.getCurrentIbi().toString());
               restIntent.putExtra("e4_temp",this.e4BandRepository.getCurrentTemp().toString());
               restIntent.putExtra("timestamp",this.instant.toString());
               restIntent.putExtra("id",this.user_id);

               MyApp.getContext().startService(restIntent);

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
