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

    @Query("DELETE FROM HEALTHBOARD WHERE id_session_local = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM HEALTHBOARD WHERE id_session_local = :id_session_local")
    List<HealthBoardEntity> getHealthBoardSessionById(int id_session_local);


}
