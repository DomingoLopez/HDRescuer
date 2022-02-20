package com.hdrescuer.hdrescuer.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.List;

@Dao
public interface TicWatchDao {

    @Insert
    void insert(TicWatchEntity ticWatch);

    @Query("DELETE FROM TICWATCH")
    void deleteAll();

    @Query("DELETE FROM TICWATCH WHERE session_id = :session_id")
    void deleteById(int session_id);

    @Query("SELECT * FROM TICWATCH WHERE session_id = :session_id")
    List<TicWatchEntity> getTicWatchSessionById(int session_id);

    @Query("SELECT MAX(tic_step) FROM TICWATCH " +
            "WHERE session_id = :session_id")
    int getTicWatchMaxStepById(int session_id);

}
