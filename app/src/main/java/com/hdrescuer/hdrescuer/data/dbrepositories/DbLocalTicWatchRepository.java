package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.EmpaticaDao;
import com.hdrescuer.hdrescuer.db.dao.TicWatchDao;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.List;

public class DbLocalTicWatchRepository {

    private TicWatchDao ticWatchDao;


    public DbLocalTicWatchRepository(Application application){
        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        ticWatchDao = db.getTicWatchDao();

    }

    public void deleteByIdSession(int id_session_local){ticWatchDao.deleteById(id_session_local);}

    public void deleteAllSession(){ticWatchDao.deleteAll();}

    public List<TicWatchEntity> getByIdSession(int id_session_local){return ticWatchDao.getTicWatchSessionById(id_session_local);}

    public void insertTicWatchData(TicWatchEntity ticWatchEntity){
        new insertTicWatchAsyncTask(ticWatchDao).execute(ticWatchEntity);
    }


    private static class insertTicWatchAsyncTask extends AsyncTask<TicWatchEntity, Void, Void>{

        private TicWatchDao ticWatchDaoAsyncTask;


        insertTicWatchAsyncTask(TicWatchDao ticWatchDao){
            ticWatchDaoAsyncTask = ticWatchDao;
        }


        @Override
        protected Void doInBackground(TicWatchEntity... ticWatchEntities) {
            ticWatchDaoAsyncTask.insert(ticWatchEntities[0]);
            return null;
        }
    }



}
