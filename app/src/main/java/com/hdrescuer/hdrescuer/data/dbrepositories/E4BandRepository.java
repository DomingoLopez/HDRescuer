package com.hdrescuer.hdrescuer.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.hdrescuer.hdrescuer.db.DataRecoveryDataBase;
import com.hdrescuer.hdrescuer.db.dao.EmpaticaDao;
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Repositorio de datos de la pulsera Empática. Implemetna la interfaz EmpaDataDelegate, del SDK de la empática
 * @author Domingo Lopez
 */
public class E4BandRepository implements EmpaDataDelegate {


    private EmpaticaDao empaticaDao;

    private final static double timezoneOffset = TimeZone.getDefault().getRawOffset() / 1000d;


    private Float battery;
    private Double tag;
    private Integer currentAccX;
    private Integer currentAccY;
    private Integer currentAccZ;
    private Float currentBvp;
    private Integer currentHr;
    private Float currentGsr;
    private Float currentIbi;
    private Float currentTemp;



    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public E4BandRepository(Application application) {

        DataRecoveryDataBase db = DataRecoveryDataBase.getDataBase(application);
        empaticaDao = db.getEmpaticaDao();

        battery = 0.0f;
        currentAccX = 0;
        currentAccY = 0;
        currentAccZ = 0;
        currentBvp = 0.0f;
        currentHr = 0;
        currentGsr = 0.0f;
        currentIbi = 0.0f;
        currentTemp = 0.0f;
        tag = 0.0;
    }



    @Override
    public void didReceiveBatteryLevel(float battery, double timestamp) {
        this.battery = battery;
    }

    @Override
    public void didReceiveAcceleration(int x, int y, int z, double timestamp) {
        currentAccX = x;
        currentAccY = y;
        currentAccZ = z;
    }

    @Override
    public void didReceiveBVP(float bvp, double timestamp) {
        currentBvp = bvp;

    }

    @Override
    public void didReceiveGSR(float gsr, double timestamp) {
        currentGsr = gsr;

    }

    @Override
    public void didReceiveIBI(float ibi, double timestamp) {



            // Escribir el HR obtenido del IBI cada segundo
            // todo: should be calculated from BVP
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {
                                if (averageHr != 0) {
                                    currentHr = Math.round(averageHr);
                                }
                        }
                    }, 0, 1000, TimeUnit.MILLISECONDS);



        final float hr = 60.0f / ibi;
        averageHr = (float) (averageHr * 0.8 + hr * 0.2);


        currentIbi = ibi;
    }

    @Override
    public void didReceiveTemperature(float temp, double timestamp) {
        currentTemp = temp;
    }

    @Override
    public void didReceiveTag(double timestamp) {
        tag = timestamp;
    }

    //Es necesario poner este método para que no pegue fallo, según la documentación del SDK de la empática
    //@Override
    public void didUpdateOnWristStatus(int status) {

    }


    /****************GETTERS*********************
     *
     *
     ********************************************/



    public static double getTimezoneOffset() {
        return timezoneOffset;
    }


    public Float getBattery() {
        return battery;
    }

    public Double getTag() {
        return tag;
    }

    public Integer getCurrentAccX() {
        return currentAccX;
    }

    public Integer getCurrentAccY() {
        return currentAccY;
    }

    public Integer getCurrentAccZ() {
        return currentAccZ;
    }

    public Float getCurrentBvp() {
        return currentBvp;
    }

    public Integer getCurrentHr() {
        return currentHr;
    }

    public Float getCurrentGsr() {
        return currentGsr;
    }

    public Float getCurrentIbi() {
        return currentIbi;
    }

    public Float getCurrentTemp() {
        return currentTemp;
    }


    /**
     * Método para resetear los valores del repositorio
     * @author Domingo Lopez
     */
    public void reset(){
        battery = 0.0f;
        currentAccX = 0;
        currentAccY = 0;
        currentAccZ = 0;
        currentBvp = 0.0f;
        currentHr = 0;
        currentGsr = 0.0f;
        currentIbi = 0.0f;
        currentTemp = 0.0f;
        tag = 0.0;
    }



    //Operaciones DAO

    public void deleteByIdSession(int id_session_local){empaticaDao.deleteById(id_session_local);}

    public void deleteAllSession(){empaticaDao.deleteAll();}

    public List<EmpaticaEntity> getByIdSession(int id_session_local){return empaticaDao.getEmpaSessionById(id_session_local);}

    public void insertEmpaticaData(EmpaticaEntity empaticaEntity){
        new E4BandRepository.insertEmpaticaAsyncTask(empaticaDao).execute(empaticaEntity);
    }


    private static class insertEmpaticaAsyncTask extends AsyncTask<EmpaticaEntity, Void, Void> {

        private EmpaticaDao empaticaDaoAsyncTask;


        insertEmpaticaAsyncTask(EmpaticaDao empaticaDao){
            empaticaDaoAsyncTask = empaticaDao;
        }


        @Override
        protected Void doInBackground(EmpaticaEntity... empaticaEntities) {
            empaticaDaoAsyncTask.insert(empaticaEntities[0]);
            return null;
        }
    }

}