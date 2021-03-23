package com.hdrescuer.hdrescuer.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;

    UserListRepository(){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();

    }

    public MutableLiveData<List<User>> getAllUsers(){
        //Obtenemos usuarios
        MutableLiveData<List<User>> data = new MutableLiveData<>();

        Call<List<User>> call = authApiService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {

                    Toast.makeText(MyApp.getContext(), "Error al cargar la lista de usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });



        //MIETNRAS QUE GENERO EL SERVIDOR
        List<User>userList= new ArrayList<>();

        User user1 = new User(1,"DOMINGO","LOPEZ PACHECO","2020-13-32");
        User user2 = new User(2,"JOSER LUIS","GARRIDO BULLEJOS","2020-13-32");
        User user3 = new User(3,"MARÍA","BERMUDEZ EDO","2020-13-32");
        User user4 = new User(4,"MARÍA JOSÉ","RODRIGUEZ FORTIZ","2020-13-32");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        data.setValue(userList);

        return data;

    }

    public void setNewUser(UserDetails user){
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        Call<User> call = authApiService.setNewUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
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
