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
import com.hdrescuer.hdrescuer.ui.ui.PruebaActivity;

import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText etEmail, etPassword;

    //Servicio de Login y ConectionClient
    ConectionClient conectionClient;
    LoginApiService loginApiService;



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

    private void retrofitInit() {
        this.conectionClient = ConectionClient.getInstance();
        this.loginApiService = conectionClient.getLoginApiService();
    }

    private void findViews() {
        this.btn_login = findViewById(R.id.btn_login);
        this.etEmail = findViewById(R.id.editTextEmail);
        this.etPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        this.btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.goToLogin();
    }


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

                        Intent i = new Intent(MainActivity.this, PruebaActivity.class);
                        startActivity(i);

                        //Destruimos el activity de login para que no se pueda volver a él
                        finish();

                    }else{
                        Toast.makeText(MainActivity.this, "Algo salió mal. Revise sus datos de acceso", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    //Toast.makeText(MainActivity.this, "Error de conexión",Toast.LENGTH_LONG).show();

                    //De momento, como no tenemos la api hecha, vamos a hacer la triquiñuela de poner aquí que pase a la siguiente pantalla
                    Intent i = new Intent(MainActivity.this, PruebaActivity.class);
                    startActivity(i);

                    //Destruimos el activity de login para que no se pueda volver a él
                    finish();
                }

            });
        }
    }
}