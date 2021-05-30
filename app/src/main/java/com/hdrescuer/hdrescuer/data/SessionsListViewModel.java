package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.List;

/**
 * ViewModel de la lista de sesiones
 * @author Domingo Lopez
 */
public class SessionsListViewModel extends AndroidViewModel {

    private SessionsListRepository sessionsListRepository;

    /**
     * Constructor
     * @author Domingo Lopez
     * @param application
     */
    public SessionsListViewModel(@NonNull Application application) {
        super(application);
        this.sessionsListRepository = new SessionsListRepository();
    }



    public MutableLiveData<List<SessionEntity>> getSessions(){
        return this.sessionsListRepository.getSessions();
    }


    public void udpateSession(SessionEntity sessionEntity){this.sessionsListRepository.updateSession(sessionEntity);}

    public void deleteSessions(){
        this.sessionsListRepository.deleteSessions();
    }

    public void deteleSessionByID(int id_session_local){this.sessionsListRepository.deleteSessionByID(id_session_local);}

    public void refreshSessions(){
        this.sessionsListRepository.refreshSessions();
    }


}
