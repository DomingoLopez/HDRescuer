package com.hdrescuer.hdrescuer.ui.ui.devicesconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.DevicesConnectionViewModel;

import java.util.Calendar;
import java.util.Date;

public class DevicesConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    //ViewModel
    DevicesConnectionViewModel devicesConnectionViewModel;

    TextView tvUsernameMonitoring;
    TextView tvDateMonitoring;
    ImageView btn_back;

    //Botones de conexión
    Button btnE4BandConnect;
    Button btnWatchConnect;

    //Botón de start monitoring
    Button btnStartMonitoring;

    int user_id;
    String user_name;
    Date currentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_connection);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        this. user_id = i.getIntExtra("id", 0);
        this.user_name = i.getStringExtra("username");

        //Obtenemos la fecha:hora actual
        this.currentDate = Calendar.getInstance().getTime();

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DevicesConnectionViewModel(getApplication(),user_id);
            }
        };

        this.devicesConnectionViewModel = new ViewModelProvider(this,factory).get(DevicesConnectionViewModel.class);

        findViews();
        events();
        loadUserData();

        checkDevicesConnectionStatus();
    }


    private void findViews() {

        this.tvUsernameMonitoring = findViewById(R.id.tvUserNameToolbarMonitoring);
        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
        this.tvDateMonitoring = findViewById(R.id.tv_date_monitoring);
        this.btnE4BandConnect = findViewById(R.id.btn_connect_e4);
        this.btnWatchConnect = findViewById(R.id.btn_connect_watch);
        this.btnStartMonitoring = findViewById(R.id.btn_start_monitoring);
    }

    private void events() {
        this.btn_back.setOnClickListener(this);
    }

    private void loadUserData() {

        this.tvUsernameMonitoring.setText(this.user_name);
        this.tvDateMonitoring.setText(this.currentDate.toString());

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
    private void checkDevicesConnectionStatus() {

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_new_monitoring:

                finish();

                break;
        }
    }
}