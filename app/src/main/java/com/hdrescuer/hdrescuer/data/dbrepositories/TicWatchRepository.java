package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.TicWatchDao;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.List;

/**
 * Repositorio de datos para el reloj Tic Watch
 * @author Domingo Lopez
 */
public class TicWatchRepository {

    private TicWatchDao ticWatchDao;

    private Integer accx;
    private Integer accy;
    private Integer accz;
    private Integer acclx;
    private Integer accly;
    private Integer acclz;
    private Integer girx;
    private Integer giry;
    private Integer girz;
    private Float hrppg;
    private Float hrppgraw;
    private Integer step;
    private int stepCounter;

    private float averageHr = 0;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public TicWatchRepository(Application application) {

        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        ticWatchDao = db.getTicWatchDao();



        this.stepCounter = 0;
        this.accx = 0;
        this.accy = 0;
        this.accz = 0;
        this.acclx = 0;
        this.accly = 0;
        this.acclz = 0;
        this.girx = 0;
        this.giry = 0;
        this.girz = 0;
        this.hrppg = 0.0f;
        this.hrppgraw = 0.0f;
        this.step = 0;


    }



    /****************GETTERS*********************
     *
     *
     ********************************************/

    public Integer getAccx() {
        return accx;
    }

    public Integer getAccy() {
        return accy;
    }

    public Integer getAccz() {
        return accz;
    }

    public Integer getAcclx() {
        return acclx;
    }

    public Integer getAccly() {
        return accly;
    }

    public Integer getAcclz() {
        return acclz;
    }

    public Integer getGirx() {
        return girx;
    }

    public Integer getGiry() {
        return giry;
    }

    public Integer getGirz() {
        return girz;
    }


    public Float getHrppg() {
        return hrppg;
    }

    public Float getHrppgraw() {
        return hrppgraw;
    }


    public Integer getStep() {
        return step;
    }

    public float getAverageHr() {
        return averageHr;
    }


    /*********************************************
     * SETTERS
     *
     ********************************************/

    public void setAccx(Float accx) { this.accx = Math.round(accx); }

    public void setAccy(Float accy) {
        this.accy = Math.round(accy);
    }

    public void setAccz(Float accz) {
        this.accz = Math.round(accz);
    }

    public void setAcclx(Float acclx) {
        this.acclx = Math.round(acclx);
    }

    public void setAccly(Float accly) {
        this.accly = Math.round(accly);
    }

    public void setAcclz(Float acclz) {
        this.acclz = Math.round(acclz);
    }

    public void setGirx(Float girx) {
        this.girx = Math.round(girx);
    }

    public void setGiry(Float giry) {
        this.giry = Math.round(giry);
    }

    public void setGirz(Float girz) {
        this.girz = Math.round(girz);
    }

    public void setHrppg(Float hrppg) {
        this.hrppg = hrppg;
    }

    public void setHrppgraw(Float hrppgraw) {
        this.hrppgraw = hrppgraw;
    }


    public void setStep(Integer step) {
        //OJO. vamos a probar a que sume los pasos
        this.step = step;
    }


    /**
     * Método que resetea los datos del repositorio
     * @author Domingo Lopez
     */
    public void reset(){
        this.stepCounter = 0;

        this.accx = 0;
        this.accy = 0;
        this.accz = 0;
        this.acclx = 0;
        this.accly = 0;
        this.acclz = 0;
        this.girx = 0;
        this.giry = 0;
        this.girz = 0;
        this.hrppg = 0.0f;
        this.hrppgraw = 0.0f;
        this.step = 0;
    }


    //Operaciones DAO

    public void deleteByIdSession(int id_session_local){ticWatchDao.deleteById(id_session_local);}

    public void deleteAllSession(){ticWatchDao.deleteAll();}

    public List<TicWatchEntity> getByIdSession(int id_session_local){return ticWatchDao.getTicWatchSessionById(id_session_local);}

    public void insertTicWatchData(TicWatchEntity ticWatchEntity){
        Log.i("INSERT DATA","INSERT DATA");
        new TicWatchRepository.insertTicWatchAsyncTask(ticWatchDao).execute(ticWatchEntity);
    }


    private static class insertTicWatchAsyncTask extends AsyncTask<TicWatchEntity, Void, Void> {

        private TicWatchDao ticWatchDaoAsyncTask;


        insertTicWatchAsyncTask(TicWatchDao ticWatchDao){
            ticWatchDaoAsyncTask = ticWatchDao;
        }


        @Override
        protected Void doInBackground(TicWatchEntity... ticWatchEntities) {
            ticWatchDaoAsyncTask.insert(ticWatchEntities[0]);
            return null;
        }
    }


    public TicWatchDao getTicWatchDao() {
        return ticWatchDao;
    }

    public void saveDBLocalData(int id_session_local, String instant){

        TicWatchEntity ticWatchEntity = new TicWatchEntity(
                id_session_local,instant, this.accx, this.accy,this.accz, this.acclx, this.accly, this.acclz, this.girx, this.giry, this.girz, this.hrppg, this.hrppgraw, this.step
        );

        this.insertTicWatchData(ticWatchEntity);
    }












}
