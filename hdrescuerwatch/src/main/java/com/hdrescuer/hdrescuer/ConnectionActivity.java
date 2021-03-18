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
import com.google.android.gms.wearable.DataItem;
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
        AmbientModeSupport.AmbientCallbackProvider,
        CapabilityClient.OnCapabilityChangedListener,
        SensorEventListener {


        static final String TAG = "ConnectionActivity";
        private Node mAndroidPhoneNodeWithApp; //Nodos encontrados
        private Boolean conectado = false;

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

        //Capa de datos para la compartición de los mismos entre el Watch y la App
        private DataClient dataClient;

        private static final String ACC_KEY = "ACC";
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/ACC");;
        PutDataRequest putDataReq;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Log.d(TAG, "onCreate()");
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_connection);

            // Enables Ambient mode.
            AmbientModeSupport.attach(this);

            // Keep the Wear screen always on (for testing only!)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }


        @Override
        protected void onPause() {
            Log.d(TAG, "onPause()");
            super.onPause();

            Wearable.getCapabilityClient(this).removeListener(this, Constants.CAPABILITY_PHONE_APP);
            this.sensorManager.unregisterListener(this);
            this.sensorManager = null;
        }

        @Override
            protected void onResume() {
            Log.d(TAG, "onResume()");
            super.onResume();

            Wearable.getCapabilityClient(this).addListener(this, Constants.CAPABILITY_PHONE_APP);

            checkIfPhoneHasApp();
        }

        /*
         * Updates UI when capabilities change (install/uninstall phone app).
         */
        public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
            Log.d(TAG, "onCapabilityChanged(): " + capabilityInfo);

            mAndroidPhoneNodeWithApp = pickBestNodeId(capabilityInfo.getNodes());
            verifyNodeAndUpdateUI();
        }

        private void checkIfPhoneHasApp() {
            Log.d(TAG, "checkIfPhoneHasApp()");

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
                //Actualizo el valor del botón para mostrarlo en Verde y a la espera
                initWatchSensors();
            } else {
                this.conectado = false;
                //Actualizo el valor del botón para mostrarlo en Rojo y a la espera de conexión
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

        //Almeceno los datos en un DataLayer entre el teléfono y el watch.

        if (event.sensor.getType() == 1) { //Acelerómetro
            String msg = "Acelerómetro: \n";
            msg += "X: " + event.values[0]+"  ";
            msg += "Y: " + event.values[1]+"  ";
            msg += "Z: " + event.values[2]+"  ";


            Log.d(TAG, msg);
        }
        else if (event.sensor.getType() == 10) { //Aceleración lineal
            String msg = "Acc Lineal: \n";
            msg += "X: " + event.values[0]+"  ";
            msg += "Y: " + event.values[1]+"  ";
            msg += "Z: " + event.values[2]+"  ";

            Log.d(TAG, msg);
        }
        else if (event.sensor.getType() == 4) { //Giroscopio
            String msg = "Giroscopio: \n";
            msg += "X: " + event.values[0]+"  ";
            msg += "Y: " + event.values[1]+"  ";
            msg += "Z: " + event.values[2]+"  ";

            Log.d(TAG, msg);
        }
        else if (event.sensor.getType() == 21) { //HRPPG
            String msg = "HR: " + event.values[0];

            Log.d(TAG, msg);
        }
        else if(event.sensor.getType() == 65572){ //HRPPGRAW
            String msg = "HRRAW: " + event.values[0];

            Log.d(TAG, msg);
        }
        else if(event.sensor.getType() == 18){ //STEPDETECTOR
            String msg = "STEP:: " + event.values[0];

            Log.d(TAG, msg);
        }
        else
            Log.d(TAG, "Unknown sensor type");
    }


    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    //Cuando cambia la precisión del sensor
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    private class MyAmbientCallback extends AmbientModeSupport.AmbientCallback {
            /** Prepares the UI for ambient mode. */
            @Override
            public void onEnterAmbient(Bundle ambientDetails) {
                super.onEnterAmbient(ambientDetails);

                Log.d(TAG, "onEnterAmbient() " + ambientDetails);
                // In our case, the assets are already in black and white, so we don't update UI.
            }

            /** Restores the UI to active (non-ambient) mode. */
            @Override
            public void onExitAmbient() {
            super.onExitAmbient();

            Log.d(TAG, "onExitAmbient()");
            // In our case, the assets are already in black and white, so we don't update UI.
            }
        }
}

