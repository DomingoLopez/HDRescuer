package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConectionClient {


    //Instancia Singleton para la conexión a la API
    private static ConectionClient instance = null;
    //Instance del Servicio de Login
    private LoginApiService loginApiService;
    //Instancia de Retrofit para establecer la conexión
    private Retrofit retrofit;

    public ConectionClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


        //creamos instancia de Retrofit con su baseUrl y le decimos que vamos a usar un conversor a JSON que es el GSON
        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build();

        this.loginApiService = this.retrofit.create(LoginApiService.class);

    }


    public static ConectionClient getInstance(){

        if(instance == null){
            instance = new ConectionClient();
        }

        return instance;
    }

    public LoginApiService getLoginApiService(){
        return this.loginApiService;
    }



}
