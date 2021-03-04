package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConectionClient {


    //Instancia Singleton para la conexión a la API
    private static ConectionClient instance = null;
    //Instance del Servicio de Login
    private LoginService loginService;
    //Instancia de Retrofit para establecer la conexión
    private Retrofit retrofit;

    public ConectionClient() {

        //creamos instancia de Retrofit con su baseUrl y le decimos que vamos a usar un conversor a JSON que es el GSON
        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        this.loginService = this.retrofit.create(LoginService.class);

    }


    public static ConectionClient getInstance(){

        if(instance == null){
            instance = new ConectionClient();
        }

        return instance;
    }

    public LoginService getLoginService(){
        return this.loginService;
    }



}
