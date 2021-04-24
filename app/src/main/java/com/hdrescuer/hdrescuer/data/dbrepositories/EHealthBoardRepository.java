package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.HealthBoardDao;
import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;

import java.util.List;

/**
 * Repositorio para la eHealthBoard
 * @author Domingo Lopez
 */
public class EHealthBoardRepository {

    private HealthBoardDao healthBoardDao;

    private Integer BPM;
    private Integer OxBlood;
    private Integer airFlow;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public EHealthBoardRepository(Application application) {

        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        healthBoardDao = db.getHealthBoardDao();


        this.BPM = 0;
       this.OxBlood = 0;
       this.airFlow = 0;
    }

    public Integer getBPM() {
        return BPM;
    }

    public void setBPM(Integer BPM) {
        this.BPM = BPM;
    }

    public Integer getOxBlood() {
        return OxBlood;
    }

    public void setOxBlood(Integer oxBlood) {
        OxBlood = oxBlood;
    }

    public Integer getAirFlow(){return this.airFlow; }

    public void setAirFlow(Integer air){this.airFlow = air;}


    /**
     * Método que resetea los valores del repositorio
     * @author Domingo Lopez
     */
    public void reset(){
        this.BPM = 0;
        this.OxBlood = 0;
        this.airFlow = 0;
    }


    //Operaciones DAO


    public void deleteByIdSession(int id_session_local){
        healthBoardDao.deleteById(id_session_local);}

    public void deleteAllSession(){
        healthBoardDao.deleteAll();}

    public List<HealthBoardEntity> getByIdSession(int id_session_local){return healthBoardDao.getHealthBoardSessionById(id_session_local);}

    public void insertHealthBoardData(HealthBoardEntity healthBoardEntity){
        new EHealthBoardRepository.insertHealthBoardAsyncTask(healthBoardDao).execute(healthBoardEntity);
    }


    private static class insertHealthBoardAsyncTask extends AsyncTask<HealthBoardEntity, Void, Void> {

        private HealthBoardDao healthBoardDaoAsyncTask;


        insertHealthBoardAsyncTask(HealthBoardDao healthBoardDao){ healthBoardDaoAsyncTask = healthBoardDao;}


        @Override
        protected Void doInBackground(HealthBoardEntity... healthBoardEntities) {
            healthBoardDaoAsyncTask.insert(healthBoardEntities[0]);
            return null;
        }
    }








}