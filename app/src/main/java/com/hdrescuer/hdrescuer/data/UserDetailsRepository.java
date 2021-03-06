package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.request.RequestUserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsRepository {

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    LiveData<UserDetails> user;

    UserDetailsRepository(int id){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        //this.user = this.getUser(id);
    }

    public LiveData<UserDetails> getUser(int id){
        final MutableLiveData<UserDetails> data = new MutableLiveData<>();

        //Obtenemos el usuario

        Call<UserDetails> call = authApiService.getUserDetails(new RequestUserDetails(id));
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al cargar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });



        UserDetails user = new UserDetails(1,
                                        "DOMINGO LÓPEZ PACHECO",
                                        29,
                                        1.73,
                                        75,
                                        "V",
                                        "domin68914@gmail.com",
                                        637447471,
                                        "2021-03-05 08:40:35");

        data.setValue(user);
        this.user = data;

        return data;

    }
}
