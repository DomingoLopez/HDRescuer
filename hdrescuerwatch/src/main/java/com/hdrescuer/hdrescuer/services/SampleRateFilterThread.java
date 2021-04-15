package com.hdrescuer.hdrescuer.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.data.DataRepository;

import java.time.Clock;
import java.time.Instant;

public class SampleRateFilterThread extends Thread{

    private DataRepository dataRepository;

    //Time Stamp utilizado de momento
    Clock clock = Clock.systemUTC();
    Instant instant;

    /**Capa de datos para la compartición de los mismos entre el Watch y la App**/
    public DataClient dataClient;
    //Atributos de compartición de datos para cada uno de los sensores
    //Acelerómetro con Gravedad
    private static final String ACCX_KEY = "ACCX";
    private static final String ACCY_KEY = "ACCY";
    private static final String ACCZ_KEY = "ACCZ";
    PutDataMapRequest putDataMapRequestACC = PutDataMapRequest.create("/ACC");;
    PutDataRequest putDataReqACC;
    //
//        //Aceleración Linear
    private static final String ACCLX_KEY = "ACCLX";
    private static final String ACCLY_KEY = "ACCLY";
    private static final String ACCLZ_KEY = "ACCLZ";
    PutDataMapRequest putDataMapRequestACCL = PutDataMapRequest.create("/ACCL");;
    PutDataRequest putDataReqACCL;
    //
//        //Giroscopio
    private static final String GIRX_KEY = "GIRX";
    private static final String GIRY_KEY = "GIRY";
    private static final String GIRZ_KEY = "GIRZ";
    PutDataMapRequest putDataMapRequestGIR = PutDataMapRequest.create("/GIR");;
    PutDataRequest putDataReqGIR;

    //HRPPG
    private static final String HRPPG_KEY = "HRPPG";
    PutDataMapRequest putDataMapRequestHRPPG = PutDataMapRequest.create("/HRPPG");;
    PutDataRequest putDataReqHRPPG;

    //HRPPGRAW
    private static final String HRPPGRAW_KEY = "HRPPGRAW";
    PutDataMapRequest putDataMapRequestHRPPGRAW = PutDataMapRequest.create("/HRPPGRAW");;
    PutDataRequest putDataReqHRPPGRAW;

    //STEP
    private static final String STEP_KEY = "STEP";
    PutDataMapRequest putDataMapRequestSTEP = PutDataMapRequest.create("/STEP");;
    PutDataRequest putDataReqSTEP;
    private int stepCounter;

    //STEP
    private static final String HB_KEY = "HB";
    PutDataMapRequest putDataMapRequestHB = PutDataMapRequest.create("/HB");;
    PutDataRequest putDataReqHB;



    private static final String TIME_KEY = "TIMEKEY";

    public static  String STATUS="ACTIVO";

    public SampleRateFilterThread(DataRepository dataRepository, DataClient dataClient){
        this.dataRepository = dataRepository;
        this.dataClient = dataClient;
    }



    @Override
    public void run(){

        try{

            while (STATUS.equals("ACTIVO")){
                Thread.sleep(Constants.SAMPLE_RATE);
                this.instant = this.clock.instant();
                sendData();

            }


        }catch (Exception e){

        }

    }

    private int i = 0;
    private void sendData(){
                //ACCELEROMETER
                this.putDataMapRequestACC.getDataMap().putFloat(ACCX_KEY,this.dataRepository.getAccx());
                this.putDataMapRequestACC.getDataMap().putFloat(ACCY_KEY, this.dataRepository.getAccy());
                this.putDataMapRequestACC.getDataMap().putFloat(ACCZ_KEY, this.dataRepository.getAccz());
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqACC = this.putDataMapRequestACC.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqACC);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                        i++;
                        Log.d("INFOWATCH", " "+i);
                    }
                });


                //ACCELEROMETER LINEAR
                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLX_KEY,this.dataRepository.getAcclx());
                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLY_KEY, this.dataRepository.getAccly());
                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLZ_KEY, this.dataRepository.getAcclz());
                this.putDataMapRequestACCL.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqACCL = this.putDataMapRequestACCL.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask1 = this.dataClient.putDataItem(this.putDataReqACCL);
                putDataTask1.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                //GIROSCOPIO
                this.putDataMapRequestGIR.getDataMap().putFloat(GIRX_KEY,this.dataRepository.getGirx());
                this.putDataMapRequestGIR.getDataMap().putFloat(GIRY_KEY, this.dataRepository.getGiry());
                this.putDataMapRequestGIR.getDataMap().putFloat(GIRZ_KEY, this.dataRepository.getGirz());
                this.putDataMapRequestGIR.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqGIR = this.putDataMapRequestGIR.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask2 = this.dataClient.putDataItem(this.putDataReqGIR);
                putDataTask2.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                //HRPPG
                this.putDataMapRequestHRPPG.getDataMap().putFloat(HRPPG_KEY,this.dataRepository.getHrppg());
                this.putDataMapRequestHRPPG.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqHRPPG = this.putDataMapRequestHRPPG.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask3 = this.dataClient.putDataItem(this.putDataReqHRPPG);
                putDataTask3.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                //HRPPGRAW
                this.putDataMapRequestHRPPGRAW.getDataMap().putFloat(HRPPGRAW_KEY,this.dataRepository.getHrppgraw());
                this.putDataMapRequestHRPPGRAW.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqHRPPGRAW = this.putDataMapRequestHRPPGRAW.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask4 = this.dataClient.putDataItem(this.putDataReqHRPPGRAW);
                putDataTask4.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                //STEPCOUNTER
                this.putDataMapRequestSTEP.getDataMap().putInt(STEP_KEY,this.dataRepository.getStepCounter());
                this.putDataMapRequestSTEP.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqSTEP = this.putDataMapRequestSTEP.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask5 = this.dataClient.putDataItem(this.putDataReqSTEP);
                putDataTask5.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                //HB
                this.putDataMapRequestHB.getDataMap().putFloat(HB_KEY,this.dataRepository.getHb());
                this.putDataMapRequestHB.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqHB = this.putDataMapRequestHB.asPutDataRequest().setUrgent();
                Task<DataItem> putDataTask6 = this.dataClient.putDataItem(this.putDataReqHB);
                putDataTask6.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        //Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

    }

}
