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
import java.util.Calendar;
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
        private int monitorizacionActiva = 0;

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
        private static final String ACC_KEY = "ACC";
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/ACC");;
        PutDataRequest putDataReq;


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
     * @param event
     */
    @Override
        public void onSensorChanged(SensorEvent event) {

        if(this.monitorizacionActiva == 1) {
            //Almeceno los datos en un DataLayer entre el teléfono y el watch.

            if (event.sensor.getType() == 1) { //Acelerómetro
                String msg = "Acelerómetro: \n";
                msg += "X: " + event.values[0] + "  ";
                msg += "Y: " + event.values[1] + "  ";
                msg += "Z: " + event.values[2] + "  ";

                this.putDataMapRequest.getDataMap().putFloat(ACC_KEY, event.values[0]);
                this.putDataReq = this.putDataMapRequest.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReq);
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

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 4) { //Giroscopio
                String msg = "Giroscopio: \n";
                msg += "X: " + event.values[0] + "  ";
                msg += "Y: " + event.values[1] + "  ";
                msg += "Z: " + event.values[2] + "  ";

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 21) { //HRPPG
                String msg = "HR: " + event.values[0];

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 65572) { //HRPPGRAW
                String msg = "HRRAW: " + event.values[0];

                Log.d(TAG, msg);
            } else if (event.sensor.getType() == 18) { //STEPDETECTOR
                String msg = "STEP:: " + event.values[0];

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
        Log.i("ENTRO","ENTRO");
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.i("INFO","RECIBIDA MONITORIZACIÓN START");
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/MONITORING") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.monitorizacionActiva = dataMap.getInt("MONITORING");
                    Log.i("MONITORING_ACTIVA", String.valueOf(dataMap.getInt("MONITORING")));
                    this.tv_status_watch.setText("MONITORIZACIÓN RECIBIDA. \n ENVIANDO DATOS...");
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

