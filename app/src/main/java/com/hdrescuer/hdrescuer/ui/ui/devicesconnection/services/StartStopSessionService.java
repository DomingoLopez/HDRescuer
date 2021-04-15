package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.JsonObject;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientSessionsModule;
import com.hdrescuer.hdrescuer.retrofit.request.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartStopSessionService extends IntentService {

    private static final String ACTION_SEND = "ACTION_SEND";

    AuthApiService authApiService;
    AuthConectionClientSessionsModule authConectionClientSessionsModule;
    ResultReceiver receiver;
    int resultCode;

    public StartStopSessionService() {
        super("StartStopSessionService");
        this.authConectionClientSessionsModule = AuthConectionClientSessionsModule.getInstance();
        this.authApiService = this.authConectionClientSessionsModule.getAuthApiService();
        this.resultCode = 9999;
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            switch (action){
                case "START_SESSION":
                    //Creamos la nueva sesión
                    Session session  = new Session(
                            intent.getStringExtra("user_id"),
                            intent.getStringExtra("timestamp_ini"),
                            intent.getStringExtra("timestamp_ini"),
                            0,
                            intent.getBooleanExtra("e4band",false),
                            intent.getBooleanExtra("ticwatch",false),
                            intent.getBooleanExtra("ehealthboard",false)
                    );
                    this.receiver = intent.getParcelableExtra("receiver");

                    //hacemos la llamada
                    initSessionCall(session);


                    break;

                case "STOP_SESSION":
                    String id_session = intent.getStringExtra("id_session");
                    String timestamp_fin = intent.getStringExtra("timestamp_fin");
                    this.receiver = intent.getParcelableExtra("receiver");

                    //hacemos la llamada
                    stopSessionCall(id_session,timestamp_fin);
                    break;

            }


        }
    }


    public void initSessionCall(Session session){
        Call<String> call = authApiService.initSession(session);
        try{
            Response<String> response = call.execute();
            String session_id = response.body();

            Bundle bundle =  new Bundle();
            bundle.putString("result", session_id);
            this.receiver.send(1, bundle);

        }catch (Exception e ){
            Bundle bundle =  new Bundle();
            bundle.putString("result", "Error al iniciar sesión");
            this.receiver.send(400, bundle);
            e.printStackTrace();
        }

        this.resultCode = 9999;


    }


    public void stopSessionCall(String id_session, String timestamp_fin){

        JsonObject obj = new JsonObject();
        obj.addProperty("id_session",id_session);
        obj.addProperty("timestamp_fin",timestamp_fin);

        Call<String> call = authApiService.stopSession( obj );
        try{
            Response<String> response = call.execute();
            String session_id = response.body();

            Bundle bundle =  new Bundle();
            bundle.putString("result", session_id);
            this.receiver.send(2, bundle);

        }catch (Exception e ){
            Bundle bundle =  new Bundle();
            bundle.putString("result", "Error al iniciar sesión");
            this.receiver.send(401, bundle);
            e.printStackTrace();
        }

        this.resultCode = 9999;
    }


}