package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.request.RequestSendData;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * IntentService que recibe los datos a mandar al servidor y los manda
 * @author Domingo Lopez
 */
public class RestSampleRateService extends IntentService {


    private static final String ACTION_SEND = "ACTION_SEND";

    AuthApiService authApiService;
    AuthConectionClient authConnectionClient;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public RestSampleRateService() {
        super("RestSampleRateService");
        this.authConnectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConnectionClient.getAuthApiService();
    }


    /**
     * Método que maneja los intents que recibe el IntentService
     * @author Domingo Lopez
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(ACTION_SEND)) {

                //Recogemos todos los parámetros y creamos el objeto a mandar por REST (de momento)
                RequestSendData newRequest = new RequestSendData(
                        intent.getIntExtra("session_id",0),
                        intent.getStringExtra("timestamp"),
                        intent.getStringExtra("tic_hrppg"),
                        intent.getStringExtra("tic_hrppgraw"),
                        intent.getStringExtra("tic_step"),
                        intent.getStringExtra("tic_accx"),
                        intent.getStringExtra("tic_accy"),
                        intent.getStringExtra("tic_accz"),
                        intent.getStringExtra("tic_acclx"),
                        intent.getStringExtra("tic_accly"),
                        intent.getStringExtra("tic_acclz"),
                        intent.getStringExtra("tic_girx"),
                        intent.getStringExtra("tic_giry"),
                        intent.getStringExtra("tic_girz"),

                        intent.getStringExtra("e4_accx"),
                        intent.getStringExtra("e4_accy"),
                        intent.getStringExtra("e4_accz"),
                        intent.getStringExtra("e4_bvp"),
                        intent.getStringExtra("e4_hr"),
                        intent.getStringExtra("e4_gsr"),
                        intent.getStringExtra("e4_ibi"),
                        intent.getStringExtra("e4_temp"),

                        intent.getStringExtra("ehb_bpm"),
                        intent.getStringExtra("ehb_o2"),
                        intent.getStringExtra("ehb_air")

                );

                Call<String> call = authApiService.setUserData(newRequest);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("RESPONSE",response.toString());
                        if(response.isSuccessful()){
                            Log.i("SUCCESS","CORRECTO");
                        }else {
                            Log.i("ERROR", "ERROR EN EL ENVÍO DE LOS DATOS");
                            DevicesConnectionActivity.crashed = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.i("ERROR", "ERROR EN EL ENVÍO DE LOS DATOS FAILURE");
                        DevicesConnectionActivity.crashed = true;
                    }
                });

            }
        }
    }


}