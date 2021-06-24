package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.E4BandRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.EHealthBoardRepository;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.time.Clock;
import java.time.Instant;

/**
 * Clase/Servicio que hereda de Thread. Su objetivo es el de realizar mediciones de los datos almacenados localmente en la App, a fin de mandarlos al servidor
 * cada X tiempo definido en una variable de clase SAMPLE_RATE en Constants.
 * @author Domingo Lopez
 */
public class SampleRateFilterThread extends Thread{

    private TicWatchRepository ticWatchRepository;
    private E4BandRepository e4BandRepository;
    private GlobalMonitoringViewModel globalMonitoringViewModel;
    private EHealthBoardRepository eHealthBoardRepository;
    long timestamp;
    public static  String STATUS="ACTIVO";
    private static String ACTION_SEND = "ACTION_SEND";

    private int session_id;

    private Instant instant;

    /**
     * Constructor de la clase, recibe los repositorios individuales y el Global Repository iniciado, así como el id_de sesión
     * @author Domingo Lopez
     * @param ticWatchRepository
     * @param e4BandRepository
     * @param eHealthBoardRepository
     * @param globalMonitoringViewModel
     * @param session_id
     */
    public SampleRateFilterThread(TicWatchRepository ticWatchRepository,
                                  E4BandRepository e4BandRepository,
                                  EHealthBoardRepository eHealthBoardRepository,
                                  GlobalMonitoringViewModel globalMonitoringViewModel, int session_id){

        this.session_id = session_id;
        this.ticWatchRepository = ticWatchRepository;
        this.e4BandRepository = e4BandRepository;
        this.eHealthBoardRepository = eHealthBoardRepository;
        this.globalMonitoringViewModel = globalMonitoringViewModel;

    }


    /**
     * Método run del Thread. Empaqueta los datos y los manda al IntentService SampleRateFilterThread
     * @author Domingo Lopez
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run(){
       this.timestamp = System.currentTimeMillis();

       try{

           while (STATUS.equals("ACTIVO")){
               Thread.sleep(Constants.SAMPLE_RATE);

               Clock reloj = Clock.systemUTC();
               this.instant = reloj.instant();
               //Clock clock = Clock.systemUTC();
               //this.instant = clock.millis(); //TimeStamp en Milisegundos. Podemos usar el instant() de arriba y tendría un formato 2021-03-23T17:40:12.356Z por ejemplo

               updateE4BandData();
               updateTicWatchData();
               updateBoardData();

               if(Constants.CONNECTION_MODE=="STREAMING" && Constants.CONNECTION_UP.equals("SI")){
                   uploadDataToServer();
               }

           }


       }catch (Exception e){
            Log.i("EXCEPTICON",""+e.toString());
       }

    }

    private void uploadDataToServer(){

        Intent restIntent = new Intent(MyApp.getContext(), RestSampleRateService.class);
        restIntent.setAction(ACTION_SEND);

        //Est lo mejor es TODO: Hacer una clase serializable y pasar el objeto entero
        //Variables a pasar del Watch

        restIntent.putExtra("tic_hrppg",this.ticWatchRepository.getHrppg().toString());
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

        restIntent.putExtra("ehb_bpm",this.eHealthBoardRepository.getBPM().toString());
        restIntent.putExtra("ehb_o2",this.eHealthBoardRepository.getOxBlood().toString());
        restIntent.putExtra("ehb_air",this.eHealthBoardRepository.getAirFlow().toString());

        restIntent.putExtra("timestamp",this.instant.toString());
        restIntent.putExtra("session_id",this.session_id);

        MyApp.getContext().startService(restIntent);


    }



    /**
     * Método para actualizar los datos de la E4 en el viewModel Global que almacena los datos
     * @author Domingo Lopez
     */
    private void updateE4BandData(){
        if(this.e4BandRepository.isConnected()) {
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

            e4BandRepository.saveDBLocalData(session_id,instant.toString());
        }


    }

    /**
     * Método para actualizar los datos del ticwatch en el viewModel Global que almacena los datos
     * @author Domingo Lopez
     */
    private void updateTicWatchData(){

        if(this.ticWatchRepository.isConnected()) {

            this.globalMonitoringViewModel.setHrppg(this.ticWatchRepository.getHrppg());
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


            ticWatchRepository.saveDBLocalData(session_id, instant.toString());
        }

    }


    /**
     * Método para actualizar los datos de la placa de salud en el viewModel Global que almacena los datos
     * @author Domingo Lopez
     */
    private void updateBoardData(){

        if(this.eHealthBoardRepository.isConnected()) {
            this.globalMonitoringViewModel.setOxi_bpm(this.eHealthBoardRepository.getBPM());
            this.globalMonitoringViewModel.setOxi_o2(this.eHealthBoardRepository.getOxBlood());
            this.globalMonitoringViewModel.setOxi_air(this.eHealthBoardRepository.getAirFlow());

            eHealthBoardRepository.saveDBLocalData(session_id,instant.toString());
        }
    }


}
