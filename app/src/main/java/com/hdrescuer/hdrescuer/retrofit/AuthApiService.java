package com.hdrescuer.hdrescuer.retrofit;

import com.google.gson.JsonObject;
import com.hdrescuer.hdrescuer.retrofit.request.RequestSendData;
import com.hdrescuer.hdrescuer.retrofit.request.Session;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Interfaz para realizar llamadas al servidor una vez estemos registrados en el sistema
 * @author Domingo Lopez
 */
public interface AuthApiService {


    /**
     * Método que realiza llamada al servidor para obtener lista corta de usuarios
     * @author Domingo Lopez
     * @return Call
     */
    @GET("api/apicomposer/get-users-short")
    Call<List<User>> getAllUsers();

    /**
     * Método que realiza llamada al servidor para obtener los detalles de un usuario
     * @author Domingo Lopez
     * @param id
     * @return Call
     */
    @GET("api/apicomposer/get-user-details/{id}")
    Call<UserDetails> getUserDetails(@Path("id") String id); //Solo viaja una id

    /**
     * Método que realiza llamada al servidor para crear un nuevo usuario
     * @author Domingo Lopez
     * @param userDetails
     * @return Call
     */
    @POST("api/users/newuser")
    Call<User> setNewUser(@Body UserDetails userDetails);

    /**
     * Método que realiza llamada al servidor para actualizar los datos del usuario
     * @author Domingo Lopez
     * @param userInfo
     * @return Call
     */
    @POST("api/users/updateuser")
    Call<String> updateUser(@Body UserInfo userInfo);

    /**
     * Método que realiza llamada al servidor para enviar paquete de datos
     * @author Domingo Lopez
     * @param userData
     * @return Call
     */
    @POST("api/datarecovery/data")
    Call<String> setUserData(@Body RequestSendData userData);

    /**
     * Método que realiza llamada al servidor para iniciar una sesión
     * @author Domingo Lopez
     * @param session
     * @return Call
     */
    @POST("api/sessions/user/init")
    Call<String> initSession(@Body Session session);

    /**
     * Método que realiza llamada al servidor para parar una sesión
     * @author Domingo Lopez
     * @param jsonObject
     * @return Call
     */
    @POST("api/sessions/stop")
    Call<String> stopSession(@Body JsonObject jsonObject);


    //Métodos para gestionar las sesiones guardadas en modo no conexión

    /**
     * Método que crea una sesión ya creada localmente en el servidor
     * @author Domingo Lopez
     * @param session
     * @return Call
     */
    @POST("api/sessions/createfromlocal")
    Call<String> createSessionFromLocal(@Body Session session);


    @Multipart
    @POST("api/datarecovery/empaticafile")
    Call<String> uploadEmpaticaCSV(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part csv
    );


    @Multipart
    @POST("api/datarecovery/ticwatchfile")
    Call<String> uploadTicWatchCSV(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part csv
    );

    @Multipart
    @POST("api/datarecovery/healthboardfile")
    Call<String> uploadHealthBoardCSV(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part csv
    );



    //Borrado de sesiones que no se quieran guardar
    /**
     * Método que borra la sesión del módulo de sesiones
     * @author Domingo Lopez
     * @param jsonObject
     * @return Call
     */
    @POST("api/sessions/deleteone")
    Call<String> deleteSession(@Body JsonObject jsonObject);


    //Borrado de sesiones que no se quieran guardar
    /**
     * Método que borra los datos del módulo de datos
     * @author Domingo Lopez
     * @param jsonObject
     * @return Call
     */
    @POST("api/datarecovery/deletedata")
    Call<String> deleteSessionData(@Body JsonObject jsonObject);




}
