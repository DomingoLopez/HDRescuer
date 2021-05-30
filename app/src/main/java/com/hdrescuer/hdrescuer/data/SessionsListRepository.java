package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SessionsListRepository {


    MutableLiveData<List<SessionEntity>> sessions;
    SessionsRepository sessionsRepository;


    SessionsListRepository(){

        //Repositorio con la conexión DAO
        this.sessionsRepository = new SessionsRepository(MyApp.getInstance());
        sessions = getAllSessions();
    }


    public MutableLiveData<List<SessionEntity>> getAllSessions(){
        if(sessions == null)
            sessions = new MutableLiveData<>();

        List<SessionEntity> sesiones_locales;
        sesiones_locales = this.sessionsRepository.getAllSession();

        sessions.setValue(sesiones_locales);

        return sessions;

    }


    public MutableLiveData<List<SessionEntity>> getSessions(){
        return this.sessions;
    }


    public void deleteSessions(){
        this.sessionsRepository.deleteAllSession();
        List<SessionEntity> sesiones_locales;
        sesiones_locales = this.sessionsRepository.getAllSession();

        sessions.setValue(sesiones_locales);
    }

    public void deleteSessionByID(int id_session_local){
        this.sessionsRepository.deleteByIdSession(id_session_local);
        //Replicación en el servidor
        deleteCurrentSessionFromServer(id_session_local);

        List<SessionEntity> sesiones_locales;
        sesiones_locales = this.sessionsRepository.getAllSession();

        sessions.setValue(sesiones_locales);
    }

    public void updateSession(SessionEntity sessionEntity){
        this.sessionsRepository.updateSession(sessionEntity);

        List<SessionEntity> tmp = this.sessions.getValue();

        for(int i = 0; i< tmp.size(); i++){
            if(tmp.get(i).id_session_local == sessionEntity.id_session_local) {
                tmp.remove(i);
                break;
            }
        }
        sessions.setValue(tmp);
    }


    public void refreshSessions(){
        this.sessions = getAllSessions();
    }


    private void deleteCurrentSessionFromServer(int session_id) {

        AuthConectionClient conectionClient;
        AuthApiService apiService;

        conectionClient = AuthConectionClient.getInstance();
        apiService = conectionClient.getAuthApiService();

        JsonObject obj = new JsonObject();
        obj.addProperty("session_id", session_id);

        //Objeto llamada con respuesta String para borrado de la sesión
        Call<Integer> call = apiService.deleteSession(obj);

        //Objeto llamada con respuesta String para borrado de la sesión
        Call<Integer> call_data = apiService.deleteSessionData(obj);


        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (response.isSuccessful()) { //Código 200...299

                } else {

                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }

        });


        call_data.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call_data, Response<Integer> response) {

                if (response.isSuccessful()) { //Código 200...299

                } else {

                }

            }

            @Override
            public void onFailure(Call<Integer> call_data, Throwable t) {

            }

        });
    }



}
