package com.hdrescuer.hdrescuer.data;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.dbrepositories.E4BandRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.EHealthBoardRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;

import java.util.List;


public class SessionsHistListRepository {


    MutableLiveData<List<SessionEntity>> sessions;
    SessionsRepository sessionsRepository;
    E4BandRepository e4BandRepository;
    TicWatchRepository ticWatchRepository;
    EHealthBoardRepository eHealthBoardRepository;
    String user_id;


    SessionsHistListRepository(String user_id){

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
        sesiones_locales = this.sessionsRepository.getAllSession();

        sessions.setValue(sesiones_locales);
    }

    public void deleteSessionByID(int id_session_local){
        this.sessionsRepository.deleteByIdSession(id_session_local);
        this.e4BandRepository.deleteByIdSession(id_session_local);
        this.eHealthBoardRepository.deleteByIdSession(id_session_local);
        this.ticWatchRepository.deleteByIdSession(id_session_local);

        sessions = getAllSessions();

    }


    public void refreshSessions(){
        this.sessions = getAllSessions();
    }


}
