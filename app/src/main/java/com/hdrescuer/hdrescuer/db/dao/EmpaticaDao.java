package com.hdrescuer.hdrescuer.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;

import java.util.List;

@Dao
public interface EmpaticaDao {

    @Insert
    void insert(EmpaticaEntity empatica);

    @Query("DELETE FROM EMPATICA")
    void deleteAll();

    @Query("DELETE FROM EMPATICA WHERE id_session_local = :id_session_local")
    void deleteById(int id_session_local);

    @Query("SELECT * FROM EMPATICA WHERE id_session_local = :id_session_local")
    List<EmpaticaEntity> getEmpaSessionById(int id_session_local);


}
