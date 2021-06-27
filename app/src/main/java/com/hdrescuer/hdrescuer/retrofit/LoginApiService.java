package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.retrofit.request.RequestLogin;
import com.hdrescuer.hdrescuer.retrofit.request.RequestServerUp;
import com.hdrescuer.hdrescuer.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Interfaz para conexiones sin estar autenticado
 * @author Domingo Lopez
 */
public interface LoginApiService {


    /**
     * Método que realiza la llamada al servidor para comprobar disponibilidad
     * @author Domingo Lopez
     * @param requestServerUp
     * @return Call
     */
    @POST("api/auth/logintest")
    Call<String> doServerTest(@Body RequestServerUp requestServerUp);


}
