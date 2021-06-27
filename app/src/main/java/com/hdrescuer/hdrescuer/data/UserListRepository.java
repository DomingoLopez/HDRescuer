package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.UsersRepository;
import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.User;

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
    //Dao para la base de datos
    UsersRepository usersRepository;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    UserListRepository(){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
        usersRepository = new UsersRepository(MyApp.getInstance());
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
        //Si disponemos de conexión, obtenemos la lista corta de usuarios
        //del servidor
        if(Constants.CONNECTION_UP.equals("SI")) {
            Call<List<User>> call = authApiService.getAllUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        users.setValue(response.body());
                    } else {
                        users.setValue(usersRepository.getUsersShort());
                    }
                }
                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    users.setValue(usersRepository.getUsersShort());
                }
            });
        //Si no disponemos de conexión, obtenemos
        //los usuarios de la base de datos local
        }else{
            users.setValue(usersRepository.getUsersShort());
        }
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
    public void setNewUser(UserEntity user){

        //Obtenemos siguiente id de usuario
        int max_id = usersRepository.getMaxUser();
        int id_final = 0;
        //Log.i("MAXIMA SESION",""+max_id);
        if(max_id >= 1){
            id_final = max_id + 1;
        }else{ //si no hay sesiones. La inicio a 1
            id_final = 1;
        }
        user.setUser_id(id_final);

        if(Constants.CONNECTION_UP.equals("SI")) {

            Call<User> call = authApiService.setNewUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        List<User> listaClonada = new ArrayList<>();
                        listaClonada.add(response.body());
                        if (users.getValue() != null) {
                            for (int i = 0; i < users.getValue().size(); i++) {
                                listaClonada.add(new User(users.getValue().get(i)));
                            }
                        }
                        users.setValue(listaClonada);

                        //Introducimos el usuario en la Base de datos
                        usersRepository.insertUser(user);

                        Toast.makeText(MyApp.getContext(), "Usuario creado de forma satisfactoria", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }else{
            Toast.makeText(MyApp.getContext(), "Estas en modo no conexión. Vuelve a iniciar sesión.", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Método que refresca los usuarios se ha habido nuevas creaciones de usuario
     * @author Domingo Lopez
     */
    public void refreshUsers(){
        this.users = getAllUsers();
    }


}
