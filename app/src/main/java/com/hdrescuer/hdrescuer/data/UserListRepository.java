package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientUsersModule;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {

    AuthApiService authApiService;
    AuthConectionClientUsersModule authConectionClientUsersModule;
    MutableLiveData<List<User>> users;

    UserListRepository(){
        this.authConectionClientUsersModule = AuthConectionClientUsersModule.getInstance();
        this.authApiService = this.authConectionClientUsersModule.getAuthApiService();
        users = getAllUsers();
    }

    public MutableLiveData<List<User>> getAllUsers(){
        if(users == null)
            users = new MutableLiveData<>();

        Call<List<User>> call = authApiService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    users.setValue(response.body());
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al cargar la lista de usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });


        return users;

    }

    public MutableLiveData<List<User>> getUsers(){
        return this.users;
    }

    public void setNewUser(UserDetails user){
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        Call<User> call = authApiService.setNewUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    List<User> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    for (int i = 0; i<users.getValue().size(); i++){
                        listaClonada.add(new User(users.getValue().get(i)));
                    }
                    users.setValue(listaClonada);

                    Toast.makeText(MyApp.getContext(), "Usuario creado de forma satisfactoria", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
