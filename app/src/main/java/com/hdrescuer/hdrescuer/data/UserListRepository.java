package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

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

/**
 * Repositorio de datos de la lista de usuarios
 * @author Domingo Lopez
 */
public class UserListRepository {

    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    MutableLiveData<List<User>> users;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    UserListRepository(){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        users = getAllUsers();
    }

    /**
     * Método que obtiene la lista de usuarios desde el servidor
     * @author Domingo Lopez
     * @return MutableLiveData
     */
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

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });


        return users;

    }

    /**
     * Método que devuelve los usuarios almacenados hasta el momento
     * @author Domingo Lopez
     * @return MutableLiveData
     */
    public MutableLiveData<List<User>> getUsers(){
        return this.users;
    }

    /**
     * Método que hace llamada al servidor para crear un nuevo usuario
     * @author Domingo Lopez
     * @param user
     */
    public void setNewUser(UserDetails user){
        Call<User> call = authApiService.setNewUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    List<User> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    if(users.getValue() != null){
                        for (int i = 0; i<users.getValue().size(); i++){
                            listaClonada.add(new User(users.getValue().get(i)));
                        }
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


    /**
     * Método que refresca los usuarios se ha habido nuevas creaciones de usuario
     * @author Domingo Lopez
     */
    public void refreshUsers(){
        this.users = getAllUsers();
    }


}
