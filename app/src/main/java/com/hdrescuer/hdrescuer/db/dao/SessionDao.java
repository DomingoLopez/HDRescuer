package com.hdrescuer.hdrescuer.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.hdrescuer.db.entity.SessionEntity;

import java.util.List;

@Dao
public interface SessionDao {

    @Insert
    void insert(SessionEntity session);

    @Update
    void update(SessionEntity session);


    @Query("SELECT MAX(id_session_local) FROM SESSION")
    int getMaxSession();

    @Query("DELETE FROM SESSION")
    void deleteAll();

    @Query("DELETE FROM SESSION WHERE id_session_local = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM SESSION ORDER BY timestamp_ini DESC")
    List<SessionEntity> getAllSessions();

    @Query("SELECT * FROM SESSION WHERE id_session_local = :id_session_local")
    SessionEntity getSessionById(int id_session_local);

}
