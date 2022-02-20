package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.UserDao;
import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;

public class PatientsRepository {

    private UserDao userDao;


    public PatientsRepository(Application application){
        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        userDao = db.getUserDao();

    }

    //Definimos todas las operaciones que vamos a hacer sobre la tabla del usuario

    public List<UserEntity> getAllUser(){ return userDao.getAllUsers();}

        public int getMaxUser(){return userDao.getMaxUser();}

    public void deleteAllUser(){
        userDao.deleteAll();}

    public void deleteByIdUser(int user_id){
        userDao.deleteById(user_id);}

    public UserEntity getByIdUser(int user_id){return userDao.getUserById(user_id);}

    public List<User> getUsersShort(){return userDao.getUsersShort();};


    public UserDetails getUsersLarge(int user_id){return  userDao.getUsersLarge(user_id);};



    public void insertUser(UserEntity user){
        new insertUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(UserEntity user){
        new updateUserAsyncTask(userDao).execute(user);
    }

    private static class insertUserAsyncTask extends AsyncTask<UserEntity, Void, Void>{

        private UserDao userDaoAsyncTask;


        insertUserAsyncTask(UserDao userDao){
            userDaoAsyncTask = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... userEntities) {

            userDaoAsyncTask.insert(userEntities[0]);
            return null;
        }
    }

    private static class updateUserAsyncTask extends AsyncTask<UserEntity, Void, Void>{

        private UserDao userDaoAsyncTask;


        updateUserAsyncTask(UserDao userDao){
            userDaoAsyncTask = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... userEntities) {

            userDaoAsyncTask.update(userEntities[0]);
            return null;
        }
    }

}
