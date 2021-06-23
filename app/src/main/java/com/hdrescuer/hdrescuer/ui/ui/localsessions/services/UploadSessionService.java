package com.hdrescuer.hdrescuer.ui.ui.localsessions.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.E4BandRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.EHealthBoardRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class UploadSessionService extends IntentService {

    private static final String ACTION_SEND = "ACTION_SEND";

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    ResultReceiver receiver;
    int resultCode;

    //Repositorios de la BBDD
    SessionsRepository sessionsRepository;
    E4BandRepository e4BandRepository;
    TicWatchRepository ticWatchRepository;
    EHealthBoardRepository healthBoardRepository;

    SessionEntity sessionEntity;
    int user_id;
    int session_id;


    private String FILE_NAME_EMPATICA;
    private String FILE_NAME_TICWATCH;
    private String FILE_NAME_HEALTHBOARD;



    public UploadSessionService() {
        super("UploadSessionService");
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();

        this.sessionsRepository = new SessionsRepository(getApplication());
        this.e4BandRepository = new E4BandRepository(getApplication());
        this.ticWatchRepository = new TicWatchRepository(getApplication());
        this.healthBoardRepository = new EHealthBoardRepository(getApplication());

        this.resultCode = 9999;
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            switch (action){
                case "START_UPLOAD":
                    //Obtenemos los parámetros

                    this.user_id = intent.getIntExtra("user_id",0);
                    this.session_id = intent.getIntExtra("session_id",0);
                    this.receiver = intent.getParcelableExtra("receiver");
                    //Iniciamos los nombres de archivo
                    this.FILE_NAME_EMPATICA = "empatica_session_"+session_id+".csv";
                    this.FILE_NAME_TICWATCH = "ticwatch_session_"+session_id+".csv";
                    this.FILE_NAME_HEALTHBOARD = "healthboard_session_"+session_id+".csv";

                    //Obtenemos la sesión que nos hará falta para el resto de métodos
                    this.sessionEntity = this.sessionsRepository.getByIdSession(session_id);

                    createSessionOnServer();

                    //Iniciamos la descarga a csv
                    downloadToCSV();

                    //Una vez guardos los datos de la sesión. Subimos los tres ficheros/ o los que haya al servidor
                    //Primero realizamos la llamada  para que cree la sesión en el servidor



                    //Subimos los archivos
                    uploadCSVToServer();

                    break;

            }


        }
    }


    public void downloadToCSV(){

        this.resultCode = 9999;

        if(this.sessionEntity.e4band){
            createEmpaticaCSV();
        }

        if(this.sessionEntity.ticwatch){
            createTicWatchCSV();
        }

        if(this.sessionEntity.ehealthboard){
            createHealthBoardCSV();
        }


    }


    void createSessionOnServer(){

        //Llamada síncrona
        Call<Integer> call = authApiService.createSessionFromLocal(new SessionEntity(
                this.session_id,
                this.user_id,
                this.sessionEntity.timestamp_ini,
                this.sessionEntity.timestamp_fin,
                this.sessionEntity.total_time,
                this.sessionEntity.e4band,
                this.sessionEntity.ticwatch,
                this.sessionEntity.ehealthboard,
                this.sessionEntity.description,
                true
        ));
        try{
            Response<Integer> response = call.execute();
           this.session_id = response.body();

        }catch (Exception e ){

            //Si algo sale mal, borramos todos los archivos, para que no dejen huella
            deleteFiles();

            Bundle bundle =  new Bundle();
            bundle.putString("result", "Error al subir sesión");
            this.resultCode = 401;
            this.receiver.send(401, bundle);
            e.printStackTrace();
        }


    }



    void createEmpaticaCSV(){

        List<EmpaticaEntity> empaticaEntities = this.e4BandRepository.getByIdSession(session_id);


        //Creamos el archivo csv
        FileOutputStream fos = null;

        try {
            fos = MyApp.getContext().openFileOutput(FILE_NAME_EMPATICA, MODE_PRIVATE);


            fos.write("SESSION_ID,TIMESTAMP,E4_ACCX,E4_ACCY,E4_ACCZ,E4_BVP,E4_HR,E4_GSR,E4_IBI,E4_TEMP\n".getBytes());

            for(int i = 0; i< empaticaEntities.size(); i++){

                fos.write((this.session_id+",").getBytes());
                fos.write(empaticaEntities.get(i).timestamp.getBytes());
                fos.write((Integer.toString(empaticaEntities.get(i).e4_accx)+",").getBytes());
                fos.write((Integer.toString(empaticaEntities.get(i).e4_accy)+",").getBytes());
                fos.write((Integer.toString(empaticaEntities.get(i).e4_accz)+",").getBytes());
                fos.write((Float.toString(empaticaEntities.get(i).e4_bvp)+",").getBytes());
                fos.write((Integer.toString(empaticaEntities.get(i).e4_hr)+",").getBytes());
                fos.write((Float.toString(empaticaEntities.get(i).e4_gsr)+",").getBytes());
                fos.write((Float.toString(empaticaEntities.get(i).e4_ibi)+",").getBytes());
                fos.write((Float.toString(empaticaEntities.get(i).e4_temp)).getBytes());

                fos.write("\n".getBytes());

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    void createTicWatchCSV(){

        List<TicWatchEntity>ticWatchEntities = this.ticWatchRepository.getByIdSession(session_id);


        //Creamos el archivo csv
        FileOutputStream fos = null;

        try {
            fos = MyApp.getContext().openFileOutput(FILE_NAME_TICWATCH, MODE_PRIVATE);


            fos.write("SESSION_ID,TIMESTAMP,TIC_ACCX,TIC_ACCY,TIC_ACCZ,TIC_ACCLX,TIC_ACCLY,TIC_ACCLZ,TIC_GIRX,TIC_GIRY,TIC_GIRZ,TIC_HRPPG,TIC_STEP\n".getBytes());

            for(int i = 0; i< ticWatchEntities.size(); i++){

                fos.write((this.session_id+",").getBytes());
                fos.write(ticWatchEntities.get(i).timestamp.getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_accx)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_accy)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_accz)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_acclx)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_accly)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_acclz)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_girx)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_giry)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_girz)+",").getBytes());
                fos.write((Float.toString(ticWatchEntities.get(i).tic_hrppg)+",").getBytes());
                fos.write((Integer.toString(ticWatchEntities.get(i).tic_step)).getBytes());

                fos.write("\n".getBytes());

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    void createHealthBoardCSV(){


        List<HealthBoardEntity>healthBoardEntities = this.healthBoardRepository.getByIdSession(session_id);


        //Creamos el archivo csv
        FileOutputStream fos = null;

        try {
            fos = MyApp.getContext().openFileOutput(FILE_NAME_HEALTHBOARD, MODE_PRIVATE);

            fos.write("SESSION_ID,TIMESTAMP,EHB_BPM,EHB_OX_BLOOD,EHB_AIR_FLOW\n".getBytes());

            for(int i = 0; i< healthBoardEntities.size(); i++){

                fos.write((this.session_id+",").getBytes());
                fos.write(healthBoardEntities.get(i).timestamp.getBytes());
                fos.write((Integer.toString(healthBoardEntities.get(i).ehb_bpm)+",").getBytes());
                fos.write((Integer.toString(healthBoardEntities.get(i).ehb_ox_blood)+",").getBytes());
                fos.write((Integer.toString(healthBoardEntities.get(i).ehb_air_flow)).getBytes());

                fos.write("\n".getBytes());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    void deleteFiles(){

        Uri uri = null;
        try {
            uri = Uri.parse((MyApp.getContext().getFilesDir().getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f_emp = new File(uri.getPath()+"/"+FILE_NAME_EMPATICA);
        File f_tic = new File(uri.getPath()+"/"+FILE_NAME_TICWATCH);
        File f_hb = new File(uri.getPath()+"/"+FILE_NAME_HEALTHBOARD);

        f_emp.delete();
        f_tic.delete();
        f_hb.delete();

    }





    void uploadCSVToServer(){

        if(this.resultCode == 9999){

            Uri uri = null;
            try {
                uri = Uri.parse((MyApp.getContext().getFilesDir().getCanonicalPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(this.sessionEntity.e4band){
                File f_emp = new File(uri.getPath() + "/" + FILE_NAME_EMPATICA);
                RequestBody filePart = RequestBody.create(MediaType.parse("text/csv"), f_emp);

                RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "empaticaCSV");
                MultipartBody.Part file = MultipartBody.Part.createFormData("empaticaCSV", f_emp.getName(), filePart);

                Call<String> call = authApiService.uploadEmpaticaCSV(descriptionPart, file);

                try {
                    Response<String> response = call.execute();
                    String result = response.body();



                } catch (Exception e) {

                    //Si algo sale mal, borramos todos los archivos, para que no dejen huella
                    deleteFiles();

                    Bundle bundle = new Bundle();
                    bundle.putString("result", "Error al subir sesión empática");
                    this.resultCode = 402;
                    this.receiver.send(401, bundle);
                    e.printStackTrace();

                    return;
                }


            }

            if(this.sessionEntity.ticwatch) {

                File f_tic = new File(uri.getPath() + "/" + FILE_NAME_TICWATCH);
                RequestBody filePart = RequestBody.create(MediaType.parse("text/csv"), f_tic);

                RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "ticwatchCSV");
                MultipartBody.Part file = MultipartBody.Part.createFormData("ticwatchCSV", f_tic.getName(), filePart);

                Call<String> call = authApiService.uploadTicWatchCSV(descriptionPart, file);

                try {
                    Response<String> response = call.execute();
                    String result = response.body();



                } catch (Exception e) {

                    //Si algo sale mal, borramos todos los archivos, para que no dejen huella
                    deleteFiles();

                    Bundle bundle = new Bundle();
                    bundle.putString("result", "Error al subir sesión ticwatch");
                    this.resultCode = 402;
                    this.receiver.send(401, bundle);
                    e.printStackTrace();

                    return;
                }

            }

            if(this.sessionEntity.ehealthboard){

                File f_hb = new File(uri.getPath() + "/" + FILE_NAME_HEALTHBOARD);
                RequestBody filePart = RequestBody.create(MediaType.parse("text/csv"), f_hb);

                RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "healthboardCSV");
                MultipartBody.Part file = MultipartBody.Part.createFormData("healthboardCSV", f_hb.getName(), filePart);

                Call<String> call = authApiService.uploadHealthBoardCSV(descriptionPart, file);

                try {
                    Response<String> response = call.execute();
                    String result = response.body();



                } catch (Exception e) {

                    //Si algo sale mal, borramos todos los archivos, para que no dejen huella
                    deleteFiles();

                    Bundle bundle = new Bundle();
                    bundle.putString("result", "Error al subir sesión healthboard");
                    this.resultCode = 402;
                    this.receiver.send(401, bundle);
                    e.printStackTrace();

                    return;
                }
            }

            //Si llego aquí,  ha ido bien y puedo borrar los archivos

            this.deleteFiles();

            Bundle bundle = new Bundle();
            bundle.putString("result", "Sesión sincronizada de forma satisfactoria");
            bundle.putInt("deleted_session", this.session_id);
            this.resultCode = 1;
            this.receiver.send(1, bundle);


        }

        this.resultCode = 9999;


    }



    //Solo por si nos hace falta
   /* void loadCSV(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(this.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String text = br.readLine();

            while(text != null){
                sb.append(text).append("\n");

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/





}