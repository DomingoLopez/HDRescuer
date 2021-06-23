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


    @Query("SELECT MAX(session_id) FROM SESSION")
    int getMaxSession();

    @Query("DELETE FROM SESSION")
    void deleteAll();

    @Query("DELETE FROM SESSION WHERE session_id = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM SESSION  WHERE user_id IS 0 ORDER BY timestamp_ini DESC")
    List<SessionEntity> getAllSessions();

    @Query("SELECT * FROM SESSION  WHERE user_id =:user_id ORDER BY timestamp_ini DESC")
    List<SessionEntity> getAllHistSessions(int user_id);

    @Query("SELECT * FROM SESSION WHERE session_id = :id_session_local")
    SessionEntity getSessionById(int id_session_local);

    @Query("SELECT * FROM SESSION WHERE session_id IN (SELECT MAX(session_id) FROM SESSION WHERE user_id = :user_id)")
    SessionEntity getMaxSessionShortByUserId(int user_id);

    

}
