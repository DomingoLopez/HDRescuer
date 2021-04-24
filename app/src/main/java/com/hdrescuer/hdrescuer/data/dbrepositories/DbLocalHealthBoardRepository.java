package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.HealthBoardDao;
import com.hdrescuer.hdrescuer.db.dao.TicWatchDao;
import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.List;

public class DbLocalHealthBoardRepository {

    private HealthBoardDao healthBoardDao;


    public DbLocalHealthBoardRepository(Application application){
        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        healthBoardDao = db.getHealthBoardDao();

    }

    public void deleteByIdSession(int id_session_local){
        healthBoardDao.deleteById(id_session_local);}

    public void deleteAllSession(){
        healthBoardDao.deleteAll();}

    public List<HealthBoardEntity> getByIdSession(int id_session_local){return healthBoardDao.getHealthBoardSessionById(id_session_local);}

    public void insertHealthBoardData(HealthBoardEntity healthBoardEntity){
        new insertHealthBoardAsyncTask(healthBoardDao).execute(healthBoardEntity);
    }


    private static class insertHealthBoardAsyncTask extends AsyncTask<HealthBoardEntity, Void, Void>{

        private HealthBoardDao healthBoardDaoAsyncTask;


        insertHealthBoardAsyncTask(HealthBoardDao healthBoardDao){ healthBoardDaoAsyncTask = healthBoardDao;}


        @Override
        protected Void doInBackground(HealthBoardEntity... healthBoardEntities) {
            healthBoardDaoAsyncTask.insert(healthBoardEntities[0]);
            return null;
        }
    }



}
