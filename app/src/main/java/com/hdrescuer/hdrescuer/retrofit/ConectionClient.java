package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente de conexión retrofit genérico
 * @author Domingo Lopez
 */
public class ConectionClient {


    //Instancia Singleton para la conexión a la API
    private static ConectionClient instance = null;
    //Instance del Servicio de Login
    private LoginApiService loginApiService;
    //Instancia de Retrofit para establecer la conexión
    private Retrofit retrofit;

    /**
     * Constructor que inicializa la conexión con retrofit
     * @author Domingo Lopez
     */
    public ConectionClient() {
        //TODO:
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // añadimos interceptor de logging
        httpClient.addInterceptor(logging);

        //creamos instancia de Retrofit con su baseUrl y le decimos que vamos a usar un conversor a JSON que es el GSON
        this.retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.DATA_RESCUER_API_GATEWAY)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build();

        this.loginApiService = this.retrofit.create(LoginApiService.class);

    }


    /**
     * Método que devuelve la instancia única del cliente
     * @author Domingo Lopez
     * @return ConectionClient
     */
    public static ConectionClient getInstance(){

        if(instance == null){
            instance = new ConectionClient();
        }

        return instance;
    }

    /**
     * Método que obtiene el servicio de llamadas al servidor
     * @author Domingo Lopez
     * @return LoginApiService
     */
    public LoginApiService getLoginApiService(){
        return this.loginApiService;
    }



}
