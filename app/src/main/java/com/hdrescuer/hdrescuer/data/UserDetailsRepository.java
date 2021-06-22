package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.UsersRepository;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
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
    MutableLiveData<UserEntity> userEntity;
    MutableLiveData<SessionEntity> sessionEntity;

    UsersRepository usersRepository;
    SessionsRepository sessionsRepository;

    /**
     * Cosntructor con parámetros, recibe un id de usuario y hace llamada al servidor
     * @author Domingo Lopez
     * @param id
     */
    UserDetailsRepository(int id){
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();

        usersRepository = new UsersRepository(MyApp.getInstance());
        sessionsRepository = new SessionsRepository(MyApp.getInstance());

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

        if(userEntity == null)
            userEntity = new MutableLiveData<>();

        if(sessionEntity == null)
            sessionEntity = new MutableLiveData<>();

        if(Constants.CONNECTION_UP.equals("SI")) {

            Call<UserDetails> call = authApiService.getUserDetails(id);
            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                    if (response.isSuccessful()) {

                        UserDetails userDet = response.body();

                        userEntity.setValue(new UserEntity(
                                userDet.getUser_id(),
                                userDet.getCreatedAt(),
                                userDet.getUsername(),
                                userDet.getLastname(),
                                userDet.getEmail(),
                                userDet.getGender(),
                                userDet.getAge(),
                                userDet.getHeight(),
                                userDet.getWeight(),
                                userDet.getPhone(),
                                userDet.getPhone2(),
                                userDet.getCity(),
                                userDet.getAddress(),
                                userDet.getCp()
                        ));

                        sessionEntity.setValue(new SessionEntity(
                                userDet.getSession_id(),
                                userDet.getUser_id(),
                                userDet.getTimestamp_ini(),
                                userDet.getTimestamp_fin(),
                                userDet.getTotal_time(),
                                userDet.isE4band(),
                                userDet.isTicwatch(),
                                userDet.isEhealthboard(),
                                userDet.getDescription()
                        ));

                        userDetails.setValue(response.body());
                    } else {



                    }
                }


                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                    Toast.makeText(MyApp.getContext(), "Error en la conexión. Accediendo en modo sin conexión", Toast.LENGTH_SHORT).show();

                }





            });

        }else{
            //Si no tenemos conexión, mostramos el usuario de la base de datos
            getUserFromDB(id);
        }


        return userDetails;

    }


    private void getUserFromDB(int id) {
        UserEntity uEnt = usersRepository.getByIdUser(id);
        SessionEntity sEnt = sessionsRepository.getMaxSessionShortByUserId(id);

        userDetails.setValue(new UserDetails(
                uEnt.getUser_id(),
                uEnt.getUsername(),
                uEnt.getLastname(),
                uEnt.getEmail(),
                uEnt.getGender(),
                uEnt.getAge(),
                uEnt.getHeight(),
                uEnt.getWeight(),
                uEnt.getPhone(),
                uEnt.getPhone2(),
                uEnt.getCity(),
                uEnt.getAddress(),
                uEnt.getCp(),
                uEnt.getCreatedAt(),
                sEnt == null ? 0 : sEnt.getSession_id(),
                sEnt == null ? null : sEnt.getTimestamp_ini(),
                sEnt == null ? null : sEnt.getTimestamp_fin(),
                sEnt == null ? 0 : sEnt.getTotal_time(),
                sEnt == null ? false : sEnt.isE4band(),
                sEnt == null ? false : sEnt.isTicwatch(),
                sEnt == null ? false : sEnt.isEhealthboard(),
                sEnt == null ? null : sEnt.getDescription()
        ));

        userEntity.setValue(uEnt);
        sessionEntity.setValue(sEnt);

    }





    public MutableLiveData<UserDetails> getUserDetails(){
        return this.userDetails;
    }

    public MutableLiveData<UserEntity> getUserEntity(){
        return this.userEntity;
    }

    public MutableLiveData<SessionEntity> getSessionEntity(){
        return this.sessionEntity;
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
