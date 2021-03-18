package com.hdrescuer.hdrescuer;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.wear.ambient.AmbientModeSupport;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.wearable.phone.PhoneDeviceType;
import android.support.wearable.view.ConfirmationOverlay;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.wearable.intent.RemoteIntent;
import com.hdrescuer.hdrescuer.common.Constants;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ConnectionActivity extends FragmentActivity implements
        DataClient.OnDataChangedListener,
        AmbientModeSupport.AmbientCallbackProvider,
        CapabilityClient.OnCapabilityChangedListener,
        SensorEventListener {

        //Nodos y estado
        static final String TAG = "ConnectionActivity";
        private Node mAndroidPhoneNodeWithApp; //Nodos encontrados
        private Boolean conectado = false;
        private String monitorizacionActiva;

        //Atributos de sensores
        SensorManager sensorManager; //SensorManager
        List<Sensor> deviceSensors; //Lista de sensores disponibles
        //Lista de sensores que vamos a usar
        Sensor accelerometer;
        Sensor accelerometerLinear;
        Sensor gyroscope;
        Sensor hrppg;
        Sensor hrppgRAW;
        Sensor stepDetector;

        //Views
        Button btn_connected_watch;
        TextView tv_status_watch;



        /**Capa de datos para la compartición de los mismos entre el Watch y la App**/
        private DataClient dataClient;
        //Atributos de compartición de datos para cada uno de los sensores
        //Acelerómetro con Gravedad
        private static final String ACCX_KEY = "ACCX";
        private static final String ACCY_KEY = "ACCY";
        private static final String ACCZ_KEY = "ACCZ";
        PutDataMapRequest putDataMapRequestACC = PutDataMapRequest.create("/ACC");;
        PutDataRequest putDataReqACC;

        //Aceleración Linear
        private static final String ACCLX_KEY = "ACCLX";
        private static final String ACCLY_KEY = "ACCLY";
        private static final String ACCLZ_KEY = "ACCLZ";
        PutDataMapRequest putDataMapRequestACCL = PutDataMapRequest.create("/ACCL");;
        PutDataRequest putDataReqACCL;

        //Giroscopio
        private static final String GIRX_KEY = "GIRX";
        private static final String GIRY_KEY = "GIRY";
        private static final String GIRZ_KEY = "GIRZ";
        PutDataMapRequest putDataMapRequestGIR = PutDataMapRequest.create("/GIR");;
        PutDataRequest putDataReqGIR;

        //HRPPG
        private static final String HRPPG_KEY = "HRPPG";
        PutDataMapRequest putDataMapRequestHRPPG = PutDataMapRequest.create("/HRPPG");;
        PutDataRequest putDataReqHRPPG;

        //HRPPGRAW
        private static final String HRPPGRAW_KEY = "HRPPGRAW";
        PutDataMapRequest putDataMapRequestHRPPGRAW = PutDataMapRequest.create("/HRPPGRAW");;
        PutDataRequest putDataReqHRPPGRAW;

        //STEP
        private static final String STEP_KEY = "STEP";
        PutDataMapRequest putDataMapRequestSTEP = PutDataMapRequest.create("/STEP");;
        PutDataRequest putDataReqSTEP;

        //Time Stamp utilizado de momento
        Clock clock = Clock.systemUTC();
        Instant instant;

        private static final String TIME_KEY = "TIMEKEY";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Log.d(TAG, "onCreate()");
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_connection);

            // Enables Ambient mode.
            //AmbientModeSupport.attach(this);

            // Keep the Wear screen always on (for testing only!)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            findViews();

        }

        private void findViews() {
            this.tv_status_watch = findViewById(R.id.tv_status_watch);
            this.btn_connected_watch = findViewById(R.id.btn_connected_watch);

            //Cargamos interfaz inicial mientras se conecta
            this.btn_connected_watch.setBackgroundColor(this.btn_connected_watch.getContext().getResources().getColor(R.color.e4Managerready,getTheme()));
            this.btn_connected_watch.setText("Comprobando");
            this.tv_status_watch.setText("Comprobando estado de vinculación entre dispositivos...");

        }

        @Override
        protected void onResume() {
            Log.d(TAG, "onResume()");
            super.onResume();

            Wearable.getCapabilityClient(this).addListener(this, Constants.CAPABILITY_PHONE_APP);
            this.dataClient = Wearable.getDataClient(this);
            Wearable.getDataClient(this).addListener(this);
            checkIfPhoneHasApp();
        }

        @Override
        protected void onPause() {
            Log.d(TAG, "onPause()");
            super.onPause();

            Wearable.getCapabilityClient(this).removeListener(this, Constants.CAPABILITY_PHONE_APP);
            Wearable.getDataClient(this).removeListener(this);
            this.sensorManager.unregisterListener(this);
            this.sensorManager = null;
        }



        public void onCapabilityChanged(CapabilityInfo capabilityInfo) {

            mAndroidPhoneNodeWithApp = pickBestNodeId(capabilityInfo.getNodes());
            verifyNodeAndUpdateUI();
        }

        private void checkIfPhoneHasApp() {

            Task<CapabilityInfo> capabilityInfoTask = Wearable.getCapabilityClient(this)
            .getCapability(Constants.CAPABILITY_PHONE_APP, CapabilityClient.FILTER_ALL);


            capabilityInfoTask.addOnCompleteListener(new OnCompleteListener<CapabilityInfo>() {
                @Override
                public void onComplete(Task<CapabilityInfo> task) {

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Capability request succeeded.");
                        CapabilityInfo capabilityInfo = task.getResult();
                        mAndroidPhoneNodeWithApp = pickBestNodeId(capabilityInfo.getNodes());

                    } else {
                        Log.d(TAG, "Capability request failed to return any results.");
                    }

                    verifyNodeAndUpdateUI();
                }
            });
        }

        private void verifyNodeAndUpdateUI() {

            if (mAndroidPhoneNodeWithApp != null) {
                this.conectado = true;
                this.btn_connected_watch.setBackgroundColor(this.btn_connected_watch.getContext().getResources().getColor(R.color.e4connected,getTheme()));
                this.btn_connected_watch.setText("Conectado");
                this.tv_status_watch.setText("Ambos dispositivos vinculados correctamente.\n Esperando monitorización...");
                this.conectado = true;
                initWatchSensors();
            } else {
                this.conectado = false;
                //Actualizo el valor del botón para mostrarlo en Rojo y a la espera de conexión
                this.btn_connected_watch.setBackgroundColor(this.btn_connected_watch.getContext().getResources().getColor(R.color.e4disconnected,getTheme()));
                this.btn_connected_watch.setText("Desconectado");
                this.tv_status_watch.setText("Los dispositivos no están vinculados. Inicie la aplicación del teléfono...");
                Log.i("ERROR","No tienes la app de móvil instalada");
            }
        }



    //Cogemos solo el primer nodo no nulo que encontremos (Solo debería haber un teléfono para un reloj)
        private Node pickBestNodeId(Set<Node> nodes) {
            Log.d(TAG, "pickBestNodeId(): " + nodes);

            Node bestNodeId = null;
            for (Node node : nodes) {
                bestNodeId = node;
            }
            return bestNodeId;
        }

        @Override
            public AmbientModeSupport.AmbientCallback getAmbientCallback() {
            return new MyAmbientCallback();
        }

        private void initWatchSensors() {

            this.sensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
            List<Sensor> deviceSensors = this.sensorManager.getSensorList(Sensor.TYPE_ALL);
            //Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE); Otra forma de hacerlo es por el tipo TYPE de sensor
            this.accelerometer = this.sensorManager.getDefaultSensor(1); //1 es el Acelerómetro
            this.accelerometerLinear = this.sensorManager.getDefaultSensor(10); //Aceleración lineal (Sin gravedad. El anterior si lleva gravedad)
            this.gyroscope = this.sensorManager.getDefaultSensor(4); //4 Giroscopio
            this.hrppg = this.sensorManager.getDefaultSensor(21); //21 el HR
            this.hrppgRAW = this.sensorManager.getDefaultSensor(65572); //65572 el HR Raw data
            this.stepDetector = this.sensorManager.getDefaultSensor(18); //18 el detector de pasos
//            Los códigos de los sensores se pueden obtener con
//            for(int i = 0; i<deviceSensors.size();i++){
//                Log.d("List sensors", "Name: "+deviceSensors.get(i).getName() + " /Type_String: " +deviceSensors.get(i).getStringType()+ " /Type_number: "+deviceSensors.get(i).getType());
//            }
            //Registramos los Listeners y indicamos el DELAY de recogida de datos UTILIZAR SENSOR_DELAY_GAME O SENSOR_DELAY_FASTEST si queremos menos retraso (Tasa de muestreo)
            this.sensorManager.registerListener(this,this.accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this,this.accelerometerLinear,SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this,this.gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this,this.hrppg,SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this,this.hrppgRAW,SensorManager.SENSOR_DELAY_NORMAL);
            this.sensorManager.registerListener(this,this.stepDetector,SensorManager.SENSOR_DELAY_NORMAL);


        }


    /**
     * Método que se dispara cada vez que cambia algún sensor. Se notifica el tipo de sensor y las lecturas vienen en event.values
     * TODO: IMPORTANTE: Tengo que revisar este método y hacer otro aparte para no repetir código.
     * @param event
     */
    @Override
        public void onSensorChanged(SensorEvent event) {


        if(this.monitorizacionActiva != null) {
            //Almeceno los datos en un DataLayer entre el teléfono y el watch.
            this.instant = this.clock.instant();
            if (event.sensor.getType() == 1) { //Acelerómetro
                String msg = "Acelerómetro: \n";
                msg += "X: " + event.values[0] + "  ";
                msg += "Y: " + event.values[1] + "  ";
                msg += "Z: " + event.values[2] + "  ";

                this.putDataMapRequestACC.getDataMap().putFloat(ACCX_KEY, event.values[0]);
                this.putDataMapRequestACC.getDataMap().putFloat(ACCY_KEY, event.values[1]);
                this.putDataMapRequestACC.getDataMap().putFloat(ACCZ_KEY, event.values[2]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqACC = this.putDataMapRequestACC.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqACC);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR ACC EN DATACLIENT");
                    }
                });

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 10) { //Aceleración lineal
                String msg = "Acc Lineal: \n";
                msg += "X: " + event.values[0] + "  ";
                msg += "Y: " + event.values[1] + "  ";
                msg += "Z: " + event.values[2] + "  ";

                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLX_KEY, event.values[0]);
                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLY_KEY, event.values[1]);
                this.putDataMapRequestACCL.getDataMap().putFloat(ACCLZ_KEY, event.values[2]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqACCL = this.putDataMapRequestACCL.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqACCL);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR ACCL EN DATACLIENT");
                    }
                });

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 4) { //Giroscopio
                String msg = "Giroscopio: \n";
                msg += "X: " + event.values[0] + "  ";
                msg += "Y: " + event.values[1] + "  ";
                msg += "Z: " + event.values[2] + "  ";

                this.putDataMapRequestGIR.getDataMap().putFloat(GIRX_KEY, event.values[0]);
                this.putDataMapRequestGIR.getDataMap().putFloat(GIRY_KEY, event.values[1]);
                this.putDataMapRequestGIR.getDataMap().putFloat(GIRZ_KEY, event.values[2]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqGIR = this.putDataMapRequestGIR.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqGIR);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR GIR EN DATACLIENT");
                    }
                });
                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 21) { //HRPPG
                String msg = "HR: " + event.values[0];
                this.putDataMapRequestHRPPG.getDataMap().putFloat(HRPPG_KEY, event.values[0]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqHRPPG = this.putDataMapRequestHRPPG.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqHRPPG);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR HR EN DATACLIENT");
                    }
                });
                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 65572) { //HRPPGRAW
                String msg = "HRRAW: " + event.values[0];
                this.putDataMapRequestHRPPGRAW.getDataMap().putFloat(HRPPGRAW_KEY, event.values[0]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqHRPPGRAW = this.putDataMapRequestHRPPGRAW.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqHRPPGRAW);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR HRRAW EN DATACLIENT");
                    }
                });
                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 18) { //STEPDETECTOR
                String msg = "STEP: " + event.values[0];
                this.putDataMapRequestSTEP.getDataMap().putFloat(STEP_KEY, event.values[0]);
                this.putDataMapRequestACC.getDataMap().putString(TIME_KEY,this.instant.toString());
                this.putDataReqSTEP = this.putDataMapRequestSTEP.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReqSTEP);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR STEP EN DATACLIENT");
                    }
                });
                Log.d(TAG, msg);
            } else
                Log.d(TAG, "Unknown sensor type");

        }
    }


    //Marca de tiempo.
    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    //Cuando cambia la precisión de los sensor
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        //Método que vamos a usar para escuchar cuando se inicia y se termina la monitorización
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/MONITORING") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.monitorizacionActiva = dataMap.getString("MONITORING");
                    Log.i("MONITORING_ACTIVADA", String.valueOf(dataMap.getInt("MONITORING")));
                    this.tv_status_watch.setText("MONITORIZACIÓN RECIBIDA. \n ENVIANDO DATOS...");
                    this.btn_connected_watch.setText("SENDING...");
                    this.btn_connected_watch.setBackgroundColor(this.btn_connected_watch.getContext().getResources().getColor(R.color.blue_secondary,getTheme()));

                }else if(item.getUri().getPath().compareTo("/MONITORINGSTOP") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.monitorizacionActiva = null;
                    Log.i("MONITORING DESACTIVADA", String.valueOf(dataMap.getInt("MONITORINGTOP")));
                    this.btn_connected_watch.setText("Conectado");
                    this.tv_status_watch.setText("Ambos dispositivos vinculados correctamente.\n Esperando monitorización...");
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }

    }

    private class MyAmbientCallback extends AmbientModeSupport.AmbientCallback {
            /** Prepares the UI for ambient mode. */
            @Override
            public void onEnterAmbient(Bundle ambientDetails) {
                super.onEnterAmbient(ambientDetails);

            }

            /** Restores the UI to active (non-ambient) mode. */
            @Override
            public void onExitAmbient() {
            super.onExitAmbient();
            }
        }
}

