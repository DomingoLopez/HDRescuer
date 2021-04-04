package com.hdrescuer.hdrescuer.ui.ui.devicesconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.EmpaticaDevice;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaStatusDelegate;
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
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.Node;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.data.E4BandRepository;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;
import com.hdrescuer.hdrescuer.data.TicWatchRepository;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring.DevicesMonitoringFragment;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.SampleRateFilterThread;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class DevicesConnectionActivity extends AppCompatActivity implements
        View.OnClickListener, EmpaStatusDelegate,
        CapabilityClient.OnCapabilityChangedListener,
        DataClient.OnDataChangedListener {

    //Repositorios
    E4BandRepository e4BandRepository;
    TicWatchRepository ticWatchRepository;
    //GlobalMonitoringRepository globalMonitoringRepository;

    //ViewModel
    GlobalMonitoringViewModel globalMonitoringViewModel;

    TextView tvUsernameMonitoring;
    TextView tvDateMonitoring;
    ImageView btn_back;

    //Botones de conexión
    Button btnE4BandConnect;
    Button btnWatchConnect;

    //Botón de start monitoring
    Button btnStartMonitoring;

    String user_id;
    String user_name;
    Date currentDate;

    //Atributos para la conexión con la E4BAND
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private EmpaDeviceManager deviceManager = null;

    EmpaStatus E4BandStatus = EmpaStatus.DISCONNECTED;

    //Atributos para la detección del Watch
    private Set<Node> wearNodesWithApp;
    private List<Node> allConnectedNodes;
    private Boolean watchConnected;

    //Atributos de compartición de datos entre la capa de datos del reloj y la app
    /**Capa de datos para la compartición de los mismos entre el Watch y la App**/
    private DataClient dataClient;
    //Atributos de compartición de datos para cada uno de los sensores
    private static final String MONITORING_KEY = "MONITORING";
    private static final String MONITORINGSTOP_KEY = "MONITORINGSTOP";
    PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/MONITORING");
    PutDataRequest putDataReq;
    PutDataMapRequest putDataMapRequestStop = PutDataMapRequest.create("/MONITORINGSTOP");
    PutDataRequest putDataReqStop;


    private SampleRateFilterThread sampleRateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_connection);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        this. user_id = i.getStringExtra("id");
        this.user_name = i.getStringExtra("username");

        //Obtenemos la fecha:hora actual
        this.currentDate = Calendar.getInstance().getTime();



        initViewModels();

        findViews();
        events();
        loadUserData();

        //Iniciamos servicios de descubrimiento para los dispositivos
        //La empática va en esta misma Actividad. Los demás en principio en servicios a parte

        //Pensar en mover esto a ONRESUME
        initEmpaticaDeviceManager();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Wearable.getCapabilityClient(this).addListener(this, Constants.CAPABILITY_WEAR_APP);
        this.dataClient = Wearable.getDataClient(this);
        Wearable.getDataClient(this).addListener(this);
        // Initial request for devices with our capability, aka, our Wear app installed.
        findWearDevicesWithApp();
        findAllWearDevices();

    }



    private void initViewModels() {

        //iniciamos Repositorios temporales
        this.e4BandRepository = new E4BandRepository();
        this.ticWatchRepository = new TicWatchRepository();
        //this.globalMonitoringRepository = new GlobalMonitoringRepository(this.user_id);

        //ViewModelFactory para el repositorio Global
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new GlobalMonitoringViewModel(getApplication(),user_id);
            }
        };
        this.globalMonitoringViewModel = new ViewModelProvider(this,factory).get(GlobalMonitoringViewModel.class);

    }


    private void findViews() {

        this.tvUsernameMonitoring = findViewById(R.id.tvUserNameToolbarMonitoring);
        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
        this.tvDateMonitoring = findViewById(R.id.tv_date_monitoring);
        this.btnStartMonitoring = findViewById(R.id.btn_start_monitoring);

        //Botones para la conexión de los dispositivos

        //E4BAND
        this.btnE4BandConnect = findViewById(R.id.btn_connect_e4);
        this.btnE4BandConnect.setOnClickListener(this);

        //WATCH. Hacerlo no clickable
        this.btnWatchConnect = findViewById(R.id.btn_connect_watch);
    }

    private void events() {
        this.btn_back.setOnClickListener(this);
        this.btnStartMonitoring.setOnClickListener(this);
    }
    private void loadUserData() {

        //Iniciamos las views a los valores iniciales por defecto
        this.tvUsernameMonitoring.setText(this.user_name);
        this.tvDateMonitoring.setText(this.currentDate.toString());

        //Botón de la empática
        this.btnE4BandConnect.setBackgroundColor(this.btnE4BandConnect.getContext().getResources().getColor(R.color.e4disconnected));
        this.btnE4BandConnect.setText("Desconectado");

        //Botón del watch
        this.btnWatchConnect.setBackgroundColor(this.btnWatchConnect.getContext().getResources().getColor(R.color.e4disconnected));
        this.btnWatchConnect.setText("Desconectado");


    }

    /**
     * Método para comprobar si los dispositivos están conectados. Si no lo están, iniciamos los servicios de conexión.
     * Una vez establecida la conexión para cada dispositivo que queramos (Al menos debe haber un dispositivo conectado)
     * al pulsar en el Botón de Start Monitoring iniciaremos el activity con tabs correspondiente y los servicios en
     * background y en distintas hebras que recibirán los datos.
     *
     * La idea es actualizar un Viewmodel con los datos y en cada cambio de los datos hacer la petición http correspondiente
     * para mandarlos, estableciendo un sistema de etiquetas para diferenciar los distintos dispositivos.
     */


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_COARSE_LOCATION:
                // Si se cancela la eleccion de permisos, no se llena el array
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permisos concedidos
                    initEmpaticaDeviceManager();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Permisos requeridos")
                            .setMessage("Sin estos permisos no podemos localizar los dispositivos bluetooth, Permite el acceso a estos permisos para utilizar la aplicación")
                            .setPositiveButton("Volver a intentarlo", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        initEmpaticaDeviceManager();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Salir ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;
        }
    }





    private void initEmpaticaDeviceManager() {
        // Android 6 (API level 23) now require ACCESS_COARSE_LOCATION permission to use BLE
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
        } else {
            if (TextUtils.isEmpty(Constants.EMPATICA_API_KEY)) {
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Please insert your API KEY")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // without permission exit is the only way
                                finish();
                            }
                        })
                        .show();
                return;
            }

            // Creamos el deviceManager y hacemos que el ViewModel que vamos a compartir con el fragment de monitorización obtenga los datos
            deviceManager = new EmpaDeviceManager(getApplicationContext(), this.e4BandRepository, this);

            // Initialize the Device Manager using your API key. You need to have Internet access at this point.
            deviceManager.authenticateWithAPIKey(Constants.EMPATICA_API_KEY);

        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_back_new_monitoring:
                String timeback = String.valueOf(System.currentTimeMillis());
                this.putDataMapRequestStop.getDataMap().putString(MONITORINGSTOP_KEY, timeback);
                this.putDataReqStop = this.putDataMapRequestStop.asPutDataRequest();
                Task <DataItem> putDataTask1 = this.dataClient.putDataItem(this.putDataReqStop);
                putDataTask1.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR STOP MONITORING EN DATACLIENT");
                    }
                });
                SampleRateFilterThread.STATUS = "INACTIVO";
                finish();

                break;

            case R.id.btn_start_monitoring:

                //Informamos al Reloj que vamos a iniciar la monitorización
                /**NO PODEMOS PONER EL MISMO VALOR AL MANDAR EL DATO, SI PONEMOS EL MISMO VALOR, EL ONCHANGED NO SE RECIBE. POR TANTO HEMOS DE HACER UN TIMESTAMP Y MANDARLO**/
                String timeStart = String.valueOf(System.currentTimeMillis());
                this.putDataMapRequest.getDataMap().putString(MONITORING_KEY, timeStart);
                this.putDataReq = this.putDataMapRequest.asPutDataRequest();
                Task<DataItem> putDataTask = this.dataClient.putDataItem(this.putDataReq);
                putDataTask.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                    @Override
                    public void onComplete(@NonNull Task<DataItem> task) {
                        Log.i("INFOTASK", "PUESTO VALOR START MONITORING EN DATACLIENT");
                    }
                });

                /*Iniciamos proceso en Background para lectura de datos según el sample rate que le pongamos
                 */
                SampleRateFilterThread.STATUS = "ACTIVO";
                this.sampleRateThread = new SampleRateFilterThread(this.ticWatchRepository, this.e4BandRepository, this.globalMonitoringViewModel, this.user_id);
                this.sampleRateThread.start();


                //Iniciaríamos el fragment para la monitorización en Tabs
                DevicesMonitoringFragment fragment = new DevicesMonitoringFragment(this.dataClient,this.putDataMapRequestStop,this.putDataReqStop);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                fragmentTransaction.add(R.id.fragment_monitoring_show, fragment);
                fragmentTransaction.commit();

                break;

        }
    }


    @Override
    public void didUpdateStatus(EmpaStatus status) {
        // Update the UI
        Log.i("STATUS",status.name());

        // The device manager is ready for use
        if (status == EmpaStatus.READY) {
            Log.i("ESTADO:",status.toString());
            Log.i("STATUS",status.name());

            // Start scanning
            try {
                deviceManager.startScanning();
                // The device manager has established a connection
            } catch (Exception e) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setCancelable(true)
                        .setMessage("Device manager is unable to download label file and may reject connecting to your device.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

            //deviceManager.startScanning();


            // The device manager has established a connection
        } else if (status == EmpaStatus.CONNECTED) {
            Log.i("ESTADO:",status.toString());

            this.btnE4BandConnect.setText("Conectado");
            this.btnE4BandConnect.setBackgroundColor(this.btnE4BandConnect.getContext().getResources().getColor(R.color.e4connected));



            // The device manager disconnected from a device
        } else if (status == EmpaStatus.DISCONNECTED) {
            Log.i("ESTADO:",status.toString());
            this.btnE4BandConnect.setText("Desconectado");
            this.btnE4BandConnect.setBackgroundColor(this.btnE4BandConnect.getContext().getResources().getColor(R.color.e4disconnected));

        }
    }

    @Override
    public void didEstablishConnection() {

    }

    @Override
    public void didUpdateSensorStatus(int status, EmpaSensorType type) {

    }

    @Override
    public void didDiscoverDevice(EmpaticaDevice bluetoothDevice, String deviceLabel, int rssi, boolean allowed) {

        // Check if the discovered device can be used with your API key. If allowed is always false,
        // the device is not linked with your API key. Please check your developer area at
        // https://www.empatica.com/connect/developer.php
        if (allowed) {
            // Stop scanning. The first allowed device will do.
            deviceManager.stopScanning();
            try {
                // Connect to the device
                deviceManager.connectDevice(bluetoothDevice);
                Log.i("ENTRO","DISCOVER");
            } catch (ConnectionNotAllowedException e) {
                // This should happen only if you try to connect when allowed == false.
                Toast.makeText(DevicesConnectionActivity.this, "Sorry, you can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void didFailedScanning(int errorCode) {

    }

    @Override
    public void didRequestEnableBluetooth() {
        // Request the user to enable Bluetooth
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void bluetoothStateChanged() {

    }

    @Override
    public void didUpdateOnWristStatus(int status) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The user chose not to enable Bluetooth
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            // You should deal with this
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (deviceManager != null) {
            deviceManager.disconnect();
        }

        //Quitamos el Listener de las capabilities del watch
        Wearable.getCapabilityClient(this).removeListener(this, Constants.CAPABILITY_WEAR_APP);
        Wearable.getDataClient(this).removeListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deviceManager != null) {
            Log.d("E4Service", "Disconnecting");
            deviceManager.disconnect();
        }
        SampleRateFilterThread.STATUS = "INACTIVE";
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {

        this.wearNodesWithApp = capabilityInfo.getNodes();

        // Because we have an updated list of devices with/without our app, we need to also update
        // our list of active Wear devices.
        findAllWearDevices();

        verifyNodeAndWaitForMonitoring();
    }


    private void findWearDevicesWithApp() {

        Task<CapabilityInfo> capabilityInfoTask;
        capabilityInfoTask = Wearable.getCapabilityClient(this)
                .getCapability(Constants.CAPABILITY_WEAR_APP, CapabilityClient.FILTER_ALL);

        capabilityInfoTask.addOnCompleteListener(new OnCompleteListener<CapabilityInfo>() {
            @Override
            public void onComplete(Task<CapabilityInfo> task) {

                if (task.isSuccessful()) {

                    CapabilityInfo capabilityInfo = task.getResult();
                    wearNodesWithApp = capabilityInfo.getNodes();

                    Log.d("INFO", "Capable Nodes: " + wearNodesWithApp);

                    verifyNodeAndWaitForMonitoring();

                } else {
                    Log.d("ERROR", "Capability request failed to return any results.");
                    btnWatchConnect.setBackgroundColor(btnWatchConnect.getContext().getResources().getColor(R.color.e4disconnected));
                    btnWatchConnect.setText("Desconectado");
                }
            }
        });
    }


    private void findAllWearDevices() {

        Task<List<Node>> NodeListTask = Wearable.getNodeClient(this).getConnectedNodes();

        NodeListTask.addOnCompleteListener(new OnCompleteListener<List<Node>>() {
            @Override
            public void onComplete(Task<List<Node>> task) {

                if (task.isSuccessful()) {
                    Log.d("INFO", "Node request succeeded.");
                    allConnectedNodes = task.getResult();

                } else {
                    Log.d("ERROR", "Node request failed to return any results.");
                    btnWatchConnect.setBackgroundColor(btnWatchConnect.getContext().getResources().getColor(R.color.e4disconnected));
                    btnWatchConnect.setText("Desconectado");
                }

                verifyNodeAndWaitForMonitoring();
            }
        });
    }


    private void verifyNodeAndWaitForMonitoring() {

        if ((this.wearNodesWithApp == null) || (this.allConnectedNodes == null)) {
            Log.d("ERROR", "Waiting on Results for both connected nodes and nodes with app");
        } else if (this.allConnectedNodes.isEmpty()) {
            Log.d("INFO", "No hay nodos conectados");
        } else if (this.wearNodesWithApp.isEmpty()) {
            Log.d("INFO", "El dispositivo WearOs no dispone de la app necesaria");
        } else if (this.wearNodesWithApp.size() < this.allConnectedNodes.size()) {
            // TODO: Add your code to communicate with the wear app(s) via
            Log.i("INFO","Algún nodo conectado. Esperando inicio de la monitorización...");
            this.btnWatchConnect.setText("Conectado");
            this.btnWatchConnect.setBackgroundColor(this.btnWatchConnect.getContext().getResources().getColor(R.color.e4connected));

        } else {
            // TODO: Add your code to communicate with the wear app(s) via
            Log.i("INFO","Todos los nodos conectados. Esperando inicio de la monitorización...");
            this.btnWatchConnect.setText("Conectado");
            this.btnWatchConnect.setBackgroundColor(this.btnWatchConnect.getContext().getResources().getColor(R.color.e4connected));
            Log.i("NODOS",this.wearNodesWithApp.toString());

        }
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {

        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/ACC") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setAccx(dataMap.getFloat("ACCX"));
                    this.ticWatchRepository.setAccy(dataMap.getFloat("ACCY"));
                    this.ticWatchRepository.setAccz(dataMap.getFloat("ACCZ"));
                }else if(item.getUri().getPath().compareTo("/ACCL") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setAcclx(dataMap.getFloat("ACCLX"));
                    this.ticWatchRepository.setAccly(dataMap.getFloat("ACCLY"));
                    this.ticWatchRepository.setAcclz(dataMap.getFloat("ACCLZ"));
                }else if(item.getUri().getPath().compareTo("/GIR") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setGirx(dataMap.getFloat("GIRX"));
                    this.ticWatchRepository.setGiry(dataMap.getFloat("GIRY"));
                    this.ticWatchRepository.setGirz(dataMap.getFloat("GIRZ"));
                }else if(item.getUri().getPath().compareTo("/HRPPG") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setHrppg(dataMap.getFloat("HRPPG"));
                }else if(item.getUri().getPath().compareTo("/HRPPGRAW") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setHrppgraw(dataMap.getFloat("HRPPGRAW"));
                }else if(item.getUri().getPath().compareTo("/HB") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setHb(dataMap.getFloat("HB"));
                }else if(item.getUri().getPath().compareTo("/STEP") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    this.ticWatchRepository.setStep(dataMap.getInt("STEP"));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}