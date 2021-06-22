package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.room.Query;
import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.SessionDao;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;


import java.util.List;

public class SessionsRepository {

    private SessionDao sessionDao;


    public SessionsRepository(Application application){
        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        sessionDao = db.getSessionDao();

    }

    //Definimos todas las operaciones que vamos a hacer sobre la tabla de la sesión


    public List<SessionEntity> getAllSession(){ return sessionDao.getAllSessions();}

    public List<SessionEntity> getAllHistSessions(String user_id){ return sessionDao.getAllHistSessions(user_id);}


    public int getMaxSession(){return sessionDao.getMaxSession();}

    public void deleteAllSession(){sessionDao.deleteAll();}

    public void deleteByIdSession(int id_session_local){sessionDao.deleteById(id_session_local);}

    public SessionEntity getByIdSession(int id_session_local){return sessionDao.getSessionById(id_session_local);}


    public SessionEntity getMaxSessionShortByUserId(int user_id){return sessionDao.getMaxSessionShortByUserId(user_id);}









    public void insertSession(SessionEntity session){
        new insertSessionAsyncTask(sessionDao).execute(session);
    }

    public void updateSession(SessionEntity session){
        new updateSessionAsyncTask(sessionDao).execute(session);
    }

    private static class insertSessionAsyncTask extends AsyncTask<SessionEntity, Void, Void>{

        private SessionDao sessionDaoAsyncTask;


        insertSessionAsyncTask(SessionDao sessionDao){
            sessionDaoAsyncTask = sessionDao;
        }

        @Override
        protected Void doInBackground(SessionEntity... sessionEntities) {

            sessionDaoAsyncTask.insert(sessionEntities[0]);
            return null;
        }
    }

    private static class updateSessionAsyncTask extends AsyncTask<SessionEntity, Void, Void>{

        private SessionDao sessionDaoAsyncTask;


        updateSessionAsyncTask(SessionDao sessionDao){
            sessionDaoAsyncTask = sessionDao;
        }

        @Override
        protected Void doInBackground(SessionEntity... sessionEntities) {

            sessionDaoAsyncTask.update(sessionEntities[0]);
            return null;
        }
    }

}
