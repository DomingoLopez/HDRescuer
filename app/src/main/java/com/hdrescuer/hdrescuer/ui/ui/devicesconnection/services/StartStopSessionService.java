package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

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

/**
 * Clase que hereda de IntentService, encargada de recibir un Intent y procesarlo. En este caso es usada para el inicio y parada de la sesión del usuario, haciendo llamadas al servidor
 * @author Domingo Lopez
 */
public class StartStopSessionService extends IntentService {

    private static final String ACTION_SEND = "ACTION_SEND";

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    ResultReceiver receiver;
    int resultCode;

    /**
     * Constructor vacío. Inicializa los atributos de instancia
     * @author Domingo Lopez
     */
    public StartStopSessionService() {
        super("StartStopSessionService");
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        this.resultCode = 9999;
    }


    /**
     * Método que maneja los intents recibidos en el IntentService
     * @author Domingo Lopez
     * @param intent
     */
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

                case "START_OFFLINE_MODE":
                    this.receiver = intent.getParcelableExtra("receiver");
                    Bundle bundle =  new Bundle();
                    bundle.putString("result", "Iniciando sesión offline");
                    this.receiver.send(1, bundle);

                    break;

                case "STOP_OFFLINE_MODE":

                    this.receiver = intent.getParcelableExtra("receiver");
                    String timestamp_fin_off = intent.getStringExtra("timestamp_fin");

                    Bundle bundle1 =  new Bundle();
                    bundle1.putString("result", "Parando sesión offline");
                    bundle1.putString("result_time", timestamp_fin_off);
                    this.receiver.send(2, bundle1);

                    break;

            }


        }
    }

    /**
     * Inicio de la sesión. Llamada al servidor con los parámetros introducidos
     * @param session
     */
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


    /**
     * Finalización de la sesión. Llamada al servidor para parar la sesión con los parámetros introducidos
     * @author Domingo Lopez
     * @param id_session
     * @param timestamp_fin
     */
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
            bundle.putString("result_time", timestamp_fin);
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