package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthConectionClient {



    private static AuthConectionClient instance = null;

    private AuthApiService authApiService;

    private Retrofit retrofit;

    public AuthConectionClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        //Incluímos en la cabecera de la petición el TOKEN que autoriza al usuario
        OkHttpClient.Builder ok = new OkHttpClient.Builder();
        ok.addInterceptor(new AuthInterceptor());
        OkHttpClient cliente = ok.build();


        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.DATA_RESCUER_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(cliente) //adjunta a todas las peticiones que hagan uso del AuthConnectionClient el token
                            .client(httpClient.build())
                            .build();

        this.authApiService = this.retrofit.create(AuthApiService.class);

    }


    public static AuthConectionClient getInstance(){

        if(instance == null){
            instance = new AuthConectionClient();
        }

        return instance;
    }

    public AuthApiService getAuthApiService(){
        return this.authApiService;
    }



}
