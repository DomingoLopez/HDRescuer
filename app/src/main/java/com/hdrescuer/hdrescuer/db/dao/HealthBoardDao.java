package com.hdrescuer.hdrescuer.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.List;

@Dao
public interface HealthBoardDao {

    @Insert
    void insert(HealthBoardEntity healtBoard);

    @Query("DELETE FROM HEALTHBOARD")
    void deleteAll();

    @Query("DELETE FROM HEALTHBOARD WHERE session_id = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM HEALTHBOARD WHERE session_id = :id_session_local")
    List<HealthBoardEntity> getHealthBoardSessionById(int id_session_local);

    @Query("SELECT MIN(ehb_ox_blood) FROM HEALTHBOARD WHERE session_id = :id_session_local AND ehb_ox_blood > 60")
    int getHealthBoardMinBloodById(int id_session_local);

    @Query("SELECT MAX(ehb_ox_blood) FROM HEALTHBOARD WHERE session_id = :id_session_local")
    int getHealthBoardMaxBloodById(int id_session_local);

    @Query("SELECT ROUND(AVG(ehb_ox_blood)) FROM HEALTHBOARD WHERE session_id = :id_session_local AND ehb_ox_blood > 20")
    int getHealthBoardAVBloodById(int id_session_local);


}
