package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.room.Insert;
import androidx.room.Query;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.EmpaticaDao;
import com.hdrescuer.hdrescuer.db.dao.SessionDao;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;

import java.util.List;

public class DbLocalEmpaticaRepository {

    private EmpaticaDao empaticaDao;


    public DbLocalEmpaticaRepository(Application application){
        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        empaticaDao = db.getEmpaticaDao();

    }

    public void deleteByIdSession(int id_session_local){empaticaDao.deleteById(id_session_local);}

    public void deleteAllSession(){empaticaDao.deleteAll();}

    public List<EmpaticaEntity> getByIdSession(int id_session_local){return empaticaDao.getEmpaSessionById(id_session_local);}

    public void insertEmpaticaData(EmpaticaEntity empaticaEntity){
        new insertEmpaticaAsyncTask(empaticaDao).execute(empaticaEntity);
    }


    private static class insertEmpaticaAsyncTask extends AsyncTask<EmpaticaEntity, Void, Void>{

        private EmpaticaDao empaticaDaoAsyncTask;


        insertEmpaticaAsyncTask(EmpaticaDao empaticaDao){
            empaticaDaoAsyncTask = empaticaDao;
        }


        @Override
        protected Void doInBackground(EmpaticaEntity... empaticaEntities) {
            empaticaDaoAsyncTask.insert(empaticaEntities[0]);
            return null;
        }
    }



}
