package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import java.util.concurrent.TimeUnit;

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
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //añadimos interceptor de logging
        httpClient.addInterceptor(logging);

        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.DATA_RESCUER_API_GATEWAY)
                            .addConverterFactory(GsonConverterFactory.create())
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
