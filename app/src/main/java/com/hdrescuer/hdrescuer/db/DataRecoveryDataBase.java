package com.hdrescuer.hdrescuer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hdrescuer.hdrescuer.db.dao.EmpaticaDao;
import com.hdrescuer.hdrescuer.db.dao.HealthBoardDao;
import com.hdrescuer.hdrescuer.db.dao.SessionDao;
import com.hdrescuer.hdrescuer.db.dao.TicWatchDao;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

@Database(entities = {SessionEntity.class, EmpaticaEntity.class, TicWatchEntity.class, HealthBoardEntity.class}, version = 1, exportSchema = false)
public abstract class DataRecoveryDataBase extends RoomDatabase {

    public abstract SessionDao getSessionDao();
    public abstract EmpaticaDao getEmpaticaDao();
    public abstract TicWatchDao getTicWatchDao();
    public abstract HealthBoardDao getHealthBoardDao();

    private static volatile DataRecoveryDataBase INSTANCE;


    public static DataRecoveryDataBase getDataBase(final Context context){

        if(INSTANCE == null){
            synchronized (DataRecoveryDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataRecoveryDataBase.class,"DATARECOVERY")
                            .build();
                }
            }
        }

        return INSTANCE;

    }

}
