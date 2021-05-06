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

    @Query("DELETE FROM TICWATCH WHERE id_session_local = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM TICWATCH WHERE id_session_local = :id_session_local")
    List<TicWatchEntity> getTicWatchSessionById(int id_session_local);

    @Query("SELECT MAX(tic_step) FROM TICWATCH WHERE id_session_local = :id_session_local")
    int getTicWatchMaxStepById(int id_session_local);



}
