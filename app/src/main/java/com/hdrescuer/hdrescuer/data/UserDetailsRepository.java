package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserInfo;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repositorio de datos de los detalles del usuario
 * @author Domingo Lopez
 */
public class UserDetailsRepository {

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    MutableLiveData<UserDetails> user;

    /**
     * Cosntructor con parámetros, recibe un id de usuario y hace llamada al servidor
     * @author Domingo Lopez
     * @param id
     */
    UserDetailsRepository(String id){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        this.user = this.getUser(id);
    }

    /**
     * Método que hace llamada al servidor para obtener la lista de usuarios en descripción corta
     * @author Domingo Lopez
     * @param id
     * @return MutableLiveData
     */
    public MutableLiveData<UserDetails> getUser(String id){

        if(user == null)
            user = new MutableLiveData<>();

        Call<UserDetails> call = authApiService.getUserDetails(id);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.isSuccessful()){
                    user.setValue(response.body());
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al cargar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });


        return user;

    }

    public MutableLiveData<UserDetails> getUser(){
        return this.user;
    }


    /**
     * Método que hace llamada de update al servidor para actualizar los datos del usuario
     * @author Domingo Lopez
     * @param user_devuelto
     */
    public void updateUser(UserInfo user_devuelto){
        Call<String> call = authApiService.updateUser(user_devuelto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MyApp.getContext(), "Usuario modificado de forma satisfactoria", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al modificar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshUserDetails(String id){
        this.user = this.getUser(id);
    }


}
