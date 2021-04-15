package com.hdrescuer.hdrescuer.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientApiComposerModule;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientUsersModule;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserInfo;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsRepository {

    AuthApiService authApiService;
    AuthApiService authApiServiceUser;
    AuthConectionClientApiComposerModule authConectionClientApiComposerModule;
    AuthConectionClientUsersModule authConectionClientUsersModule;
    MutableLiveData<UserDetails> user;

    UserDetailsRepository(String id){
        this.authConectionClientApiComposerModule = AuthConectionClientApiComposerModule.getInstance();
        this.authConectionClientUsersModule = AuthConectionClientUsersModule.getInstance();
        this.authApiService = this.authConectionClientApiComposerModule.getAuthApiService();
        this.authApiServiceUser = this.authConectionClientUsersModule.getAuthApiService();
        this.user = this.getUser(id);
    }

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


    public void updateUser(UserInfo user_devuelto){
        Call<String> call = authApiServiceUser.updateUser(user_devuelto);
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
