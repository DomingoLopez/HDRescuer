package com.hdrescuer.hdrescuer.ui.ui.localsessions.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.gson.JsonObject;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.request.Session;

import retrofit2.Call;
import retrofit2.Response;

public class UploadSessionService extends IntentService {

    private static final String ACTION_SEND = "ACTION_SEND";

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    ResultReceiver receiver;
    int resultCode;


    public UploadSessionService() {
        super("UploadSessionService");
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        this.resultCode = 9999;
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            switch (action){
                case "START_UPLOAD":
                    //Obtenemos los paráemtros

                    String user_id = intent.getStringExtra("user_id");
                    int id_session_local = intent.getIntExtra("timestamp_ini",0);
                    this.receiver = intent.getParcelableExtra("receiver");

                    //Iniciamos la descarga a csv
                    downloadToCSV(user_id,id_session_local);


                    break;

            }


        }
    }


    public void downloadToCSV(String user_id, int id_session_local){

        this.resultCode = 9999;


    }





}