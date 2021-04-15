package com.hdrescuer.hdrescuer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.SharedPreferencesManager;
import com.hdrescuer.hdrescuer.retrofit.ConectionClient;
import com.hdrescuer.hdrescuer.retrofit.LoginApiService;
import com.hdrescuer.hdrescuer.retrofit.request.RequestLogin;
import com.hdrescuer.hdrescuer.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad principal de la aplicación. Se muestra el login de usuario
 * @author Domingo Lopez
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText etEmail, etPassword;

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
        this.etEmail = findViewById(R.id.editTextEmail);
        this.etPassword = findViewById(R.id.editTextPassword);
    }

    /**
     * Método que inicia los eventos de click
     * @author Domingo Lopez
     */
    private void events() {
        this.btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.goToLogin();
    }


    /**
     * Método que valida el email y password del usuario y realizar petición asíncrona al servidor. Almacena el Token recibido y preferencias generales del usuario para posteriores llamadas
     * @author Domingo Lopez
     */
    private void goToLogin() {
        String email = this.etEmail.getText().toString();
        String pass = this.etPassword.getText().toString();

        //Validación
        if(email.isEmpty())
            this.etEmail.setError("Email requerido");
        else if (pass.isEmpty())
            this.etPassword.setError("Contraseña requerida");
        else{

            //Objeto de tipo RequestLogin
            RequestLogin requestLogin = new RequestLogin(email, pass);
            //Objeto llamada con respuesta como objeto de tipo ResponseAuth que hemos creado
            Call<ResponseAuth> call = this.loginApiService.doLogin(requestLogin);



            //Relizamos una llamada ASÍNCRONA, por lo que tenemos dos métodos, onRespose que es success y onFailure
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {



                    if(response.isSuccessful()) { //Código 200...299

                        Toast.makeText(MainActivity.this, "Sesión iniciada correctamente", Toast.LENGTH_LONG).show();

                        //Almacenamos el token y demás preferencias que devuelve la petición de OK para que estén disponibles en cualquier momento:

                       //Token
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_TOKEN,response.body().getToken());
                        //username
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_USERNAME,response.body().getUsername());
                        //email
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_EMAIL,response.body().getEmail());
                        //created
                        SharedPreferencesManager.setSomeStringValue(Constants.PREF_CREATED,response.body().getCreated());

                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);

                        //Destruimos el activity de login para que no se pueda volver a él
                        finish();

                    }else{
                        Toast.makeText(MainActivity.this, "Algo salió mal. Revise sus datos de acceso", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "No se dispone de conexión o el servidor está caído",Toast.LENGTH_LONG).show();
                }

            });
        }
    }
}