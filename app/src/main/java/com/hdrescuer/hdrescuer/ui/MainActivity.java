package com.hdrescuer.hdrescuer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.retrofit.ConectionClient;
import com.hdrescuer.hdrescuer.retrofit.LoginApiService;
import com.hdrescuer.hdrescuer.retrofit.request.RequestServerUp;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad principal de la aplicación. Se muestra el login de usuario
 * @author Domingo Lopez
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    Button btn_login_no_connection;
    ProgressBar progressBar;
    //Servicio de Login y ConectionClient
    ConectionClient conectionClient;
    LoginApiService loginApiService;


    /**
     * Inicia la actividad MainActivity, iniciando el cliente de conexión y las vistas, así como los eventos de click
     * @author Domingo Lopez
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        this.retrofitInit();
        this.findViews();
        this.events();
        
    }

    /**
     * Inicia el cliente de conexión a la red
     * @author Domingo Lopez
     */
    private void retrofitInit() {
        this.conectionClient = ConectionClient.getInstance();
        this.loginApiService = conectionClient.getLoginApiService();
    }

    /**
     * Método que busca las vistas del layout cargado
     * @author Domingo Lopez
     */
    private void findViews() {
        this.btn_login = findViewById(R.id.btn_login);
        this.btn_login_no_connection = findViewById(R.id.btn_login_no_connection);
        this.progressBar = findViewById(R.id.indeterminateBar);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Método que inicia los eventos de click
     * @author Domingo Lopez
     */
    private void events() {

        this.btn_login.setOnClickListener(this);
        this.btn_login_no_connection.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                Constants.CONNECTION_MODE = "STREAMING";
                //Comprobamos si el servidor está up y hay conexión a internet
                checkServerUp();
                break;


            case R.id.btn_login_no_connection:
                Constants.CONNECTION_MODE="FASTMODE";
                Intent i_offline = new Intent(MainActivity.this, DevicesConnectionActivity.class);
                startActivity(i_offline);
                break;
        }
    }

    private void checkServerUp() {


            RequestServerUp requestServerUp = new RequestServerUp("test");
            Call<String> call = this.loginApiService.doServerTest(requestServerUp);

            this.progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if(response.isSuccessful()) { //Código 200...299

                        Constants.CONNECTION_UP = "SI";
                        Constants.CONNECTION_MODE ="STREAMING";

                    }else{
                        Toast.makeText(MainActivity.this, "No se dispone de conexión o el servidor está caído",Toast.LENGTH_LONG).show();
                        Constants.CONNECTION_UP = "NO";
                        Constants.CONNECTION_MODE ="OFFLINE";
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    //Iniciamos la aplicación
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "No se dispone de conexión o el servidor está caído",Toast.LENGTH_LONG).show();
                    Constants.CONNECTION_UP = "NO";
                    Constants.CONNECTION_MODE ="OFFLINE";

                    progressBar.setVisibility(View.INVISIBLE);
                    //Iniciamos la aplicación
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }

            });


        }
}