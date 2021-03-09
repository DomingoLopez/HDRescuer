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

public class DevicesConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    //ViewModel
    DevicesConnectionViewModel devicesConnectionViewModel;

    TextView tvUsernameMonitoring;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_connection);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);
        String username = i.getStringExtra("username");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DevicesConnectionViewModel(getApplication(),id);
            }
        };

        this.devicesConnectionViewModel = new ViewModelProvider(this,factory).get(DevicesConnectionViewModel.class);

        findViews();
        events();
        loadUserData();
    }

    private void findViews() {

        this.tvUsernameMonitoring = findViewById(R.id.tvUserNameToolbarMonitoring);
        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
    }

    private void events() {
        this.btn_back.setOnClickListener(this);
    }

    private void loadUserData() {

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