package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.UsersRepository;
import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


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
    MutableLiveData<UserDetails> userDetails;

    UsersRepository usersRepository;


    /**
     * Cosntructor con parámetros, recibe un id de usuario y hace llamada al servidor
     * @author Domingo Lopez
     * @param id
     */
    UserDetailsRepository(int id){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        this.usersRepository = new UsersRepository(MyApp.getInstance());
        this.userDetails = this.getUser(id);
    }

    /**
     * Método que hace llamada al servidor para obtener la lista de usuarios en descripción corta
     * @author Domingo Lopez
     * @param id
     * @return MutableLiveData
     */
    public MutableLiveData<UserDetails> getUser(int id){

        if(userDetails == null)
            userDetails = new MutableLiveData<>();


        if(Constants.CONNECTION_UP.equals("SI")) {

            Call<UserDetails> call = authApiService.getUserDetails(id);
            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                    if (response.isSuccessful()) {
                        //userDetails.setValue(response.body());
                        userDetails.setValue(usersRepository.getUsersLarge(id));
                    } else {

                        userDetails.setValue(usersRepository.getUsersLarge(id));

                    }
                }


                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                    userDetails.setValue(usersRepository.getUsersLarge(id));

                }


            });

        }else{
            //Si no tenemos conexión, mostramos el usuario de la base de datos
            userDetails.setValue(usersRepository.getUsersLarge(id));
        }


        return userDetails;

    }


    public MutableLiveData<UserDetails> getUserDetails(){
        return this.userDetails;
    }


    /**
     * Método que hace llamada de update al servidor para actualizar los datos del usuario
     * @author Domingo Lopez
     * @param user_devuelto
     */
    public void updateUser(UserEntity user_devuelto){
        Call<String> call = authApiService.updateUser(user_devuelto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    //Hacemos update en nuestra base de datos
                    usersRepository.updateUser(user_devuelto);

                    Toast.makeText(MyApp.getContext(), "Usuario modificado de forma satisfactoria", Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(MyApp.getContext(), "Error al modificar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión. No se pudo modificar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshUserDetails(int id){
        this.userDetails = this.getUser(id);
    }


}
