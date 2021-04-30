package com.hdrescuer.hdrescuer.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

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


    public void refreshSessions(){
        this.sessions = getAllSessions();
    }


}
