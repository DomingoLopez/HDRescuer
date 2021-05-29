package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.db.entity.SessionEntity;

import java.util.List;

/**
 * ViewModel de la lista de usuarios
 * @author Domingo Lopez
 */
public class SessionsHistListViewModel extends AndroidViewModel implements ViewModelProvider.Factory{

    private SessionsHistListRepository sessionsHistListRepository;
    String user_id;

    /**
     * Constructor
     * @author Domingo Lopez
     * @param application
     */
    public SessionsHistListViewModel(@NonNull Application application, String user_id) {
        super(application);
        this.user_id = user_id;
        this.sessionsHistListRepository = new SessionsHistListRepository(user_id);
    }



    public MutableLiveData<List<SessionEntity>> getSessions(){
        return this.sessionsHistListRepository.getSessions();
    }


    public void deleteSessions(){
        this.sessionsHistListRepository.deleteSessions();
    }

    public void deteleSessionByID(int id_session_local){this.sessionsHistListRepository.deleteSessionByID(id_session_local);}

    public void refreshSessions(){
        this.sessionsHistListRepository.refreshSessions();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}
