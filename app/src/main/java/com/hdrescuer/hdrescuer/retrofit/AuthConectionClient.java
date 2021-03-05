package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthConectionClient {



    private static AuthConectionClient instance = null;

    private AuthApiService authApiService;

    private Retrofit retrofit;

    public AuthConectionClient() {

        //Incluímos en la cabecera de la petición el TOKEN que autoriza al usuario
        OkHttpClient.Builder ok = new OkHttpClient.Builder();
        ok.addInterceptor(new AuthInterceptor());
        OkHttpClient cliente = ok.build();


        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(cliente) //adjunta a todas las peticiones que hagan uso del AuthConnectionClient el token
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
