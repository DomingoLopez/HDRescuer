package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthConectionClientDataModule {



    private static AuthConectionClientDataModule instance = null;

    private AuthApiService authApiService;

    private Retrofit retrofit;

    public AuthConectionClientDataModule() {

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
                            .baseUrl(Constants.DATA_RESCUER_DATA_MODULE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(cliente) //adjunta a todas las peticiones que hagan uso del AuthConnectionClient el token
                            .client(httpClient.build())
                            .build();

        this.authApiService = this.retrofit.create(AuthApiService.class);

    }


    public static AuthConectionClientDataModule getInstance(){

        if(instance == null){
            instance = new AuthConectionClientDataModule();
        }

        return instance;
    }

    public AuthApiService getAuthApiService(){
        return this.authApiService;
    }



}
