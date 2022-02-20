package com.hdrescuer.hdrescuer.data;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.E4BandRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.EHealthBoardRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SessionsHistListRepository {


    MutableLiveData<List<SessionEntity>> sessions;
    SessionsRepository sessionsRepository;
    E4BandRepository e4BandRepository;
    TicWatchRepository ticWatchRepository;
    EHealthBoardRepository eHealthBoardRepository;
    int user_id;


    SessionsHistListRepository(int user_id){

        //Repositorio con la conexión DAO
        this.sessionsRepository = new SessionsRepository(MyApp.getInstance());
        this.eHealthBoardRepository = new EHealthBoardRepository(MyApp.getInstance());
        this.ticWatchRepository = new TicWatchRepository(MyApp.getInstance());
        this.e4BandRepository = new E4BandRepository(MyApp.getInstance());
        this.user_id = user_id;
        sessions = getAllSessions();
    }


    public MutableLiveData<List<SessionEntity>> getAllSessions(){
        if(sessions == null)
            sessions = new MutableLiveData<>();

        List<SessionEntity> sesiones;
        sesiones = this.sessionsRepository.getAllHistSessions(this.user_id);
        sessions.setValue(sesiones);

        return sessions;

    }


    public MutableLiveData<List<SessionEntity>> getSessions(){
        return this.sessions;
    }


    public void deleteSessions(){
        this.sessionsRepository.deleteAllSession();
        List<SessionEntity> sesiones_locales;
        sesiones_locales = this.sessionsRepository.getAllFastModeSessions();

        sessions.setValue(sesiones_locales);
    }

    public void deleteSessionByID(int id_session_local){
        this.sessionsRepository.deleteByIdSession(id_session_local);
        this.e4BandRepository.deleteByIdSession(id_session_local);
        this.eHealthBoardRepository.deleteByIdSession(id_session_local);
        this.ticWatchRepository.deleteByIdSession(id_session_local);

        //replicación en ell servidor
        deleteCurrentSessionFromServer(id_session_local);

        sessions = getAllSessions();

    }

    public void updateSession(SessionEntity sessionEntity){
        this.sessionsRepository.updateSession(sessionEntity);

        List<SessionEntity> tmp = this.sessions.getValue();

        for(int i = 0; i< tmp.size(); i++){
            if(tmp.get(i).session_id == sessionEntity.session_id) {
                tmp.get(i).setSync(true);
                tmp.get(i).setCrashed(false);
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
