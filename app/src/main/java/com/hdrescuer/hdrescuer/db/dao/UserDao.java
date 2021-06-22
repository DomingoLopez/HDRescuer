package com.hdrescuer.hdrescuer.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);


    @Query("SELECT MAX(user_id) FROM USER")
    int getMaxUser();

    @Query("DELETE FROM USER")
    void deleteAll();

    @Query("DELETE FROM USER WHERE user_id = :user_id")
    void deleteById(int user_id);

    @Query("SELECT * FROM USER ")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM USER WHERE user_id = :user_id")
    UserEntity getUserById(int user_id);

    @Query("SELECT USER.user_id, USER.username, USER.lastname, SESSION.session_id, SESSION.timestamp_ini, SESSION.total_time " +
            "FROM USER LEFT JOIN SESSION " +
            "ON USER.user_id = SESSION.user_id " +
            "AND SESSION.session_id  IN (SELECT session_id from (SELECT session_id,MAX(timestamp_ini) FROM SESSION GROUP BY session_id))")
    List<User> getUsersShort();

}
