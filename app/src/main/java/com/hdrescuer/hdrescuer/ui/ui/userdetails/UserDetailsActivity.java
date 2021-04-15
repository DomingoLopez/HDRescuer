package com.hdrescuer.hdrescuer.ui.ui.userdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.UserDetailsViewModel;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientUsersModule;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;
import com.hdrescuer.hdrescuer.common.NewUserDialogFragment;
import com.hdrescuer.hdrescuer.common.UserActionDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {



    //Servicio de Login y ConectionClient
    AuthConectionClientUsersModule authConectionClientUsersModule;
    AuthApiService authApiService;
    //ViewModel
    UserDetailsViewModel userDetailsViewModel;
    //Parámetros de la ficha del usuario
    String id;
    TextView username;
    TextView height;
    TextView weight;
    TextView age;
    TextView gender;
    TextView email;
    TextView phone;
    TextView phone2;
    TextView address;
    TextView city;
    TextView cp;
    TextView last_monitoring;
    ImageView btn_back;
    Button btn_new_monitoring;
    Button btn_edit_data;

    //Cardview ultima monitorización
    TextView tvnosession;
    TextView tvlabelLastSession;
    ImageView e4;
    ImageView tic;
    ImageView board;
    TextView last_ini;
    TextView last_fin;
    TextView last_total;

    TextView tvini;
    TextView tvfin;
    TextView tvtotal;



    UserDetails user;
    private DateFormat dateFormat;

    boolean alreadyCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        String id = i.getStringExtra("id");

        //Iniciamos el dateformat
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new UserDetailsViewModel(getApplication(),id);
            }
        };

        this.userDetailsViewModel = new ViewModelProvider(this,factory).get(UserDetailsViewModel.class);

        findViews();
        events();
        loadUserData();
        this.alreadyCreated = true;

    }


    @Override
    protected void onResume() {

        super.onResume();
        if(!alreadyCreated){
            refreshUserDetails();
        }
        alreadyCreated = false;


    }

    private void events() {

        this.btn_back.setOnClickListener(this);
        this.btn_new_monitoring.setOnClickListener(this);
        this.btn_edit_data.setOnClickListener(this);
    }

    private void findViews() {

        this.username = findViewById(R.id.tvUserNameToolbarMonitoring);
        this.age = findViewById(R.id.tvAge);
        this.height = findViewById(R.id.tvHeight);
        this.weight = findViewById(R.id.tvWeight);
        this.gender = findViewById(R.id.tvGender);
        this.email = findViewById(R.id.tvEmail);
        this.phone = findViewById(R.id.tvPhone);
        this.phone2 = findViewById(R.id.tvPhone2);
        this.address = findViewById(R.id.tvaddress);
        this.city = findViewById(R.id.tvcity);
        this.cp = findViewById(R.id.tvcp);


        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
        this.btn_new_monitoring = findViewById(R.id.btn_new_monitoring);
        this.btn_edit_data = findViewById(R.id.btn_edit_data);

        //Ultima sesión

        this.tvnosession = findViewById(R.id.tv_no_session_label);
        this.tvlabelLastSession = findViewById(R.id.label_last_monitoring);
        this.e4 = findViewById(R.id.last_session_e4);
        this.tic = findViewById(R.id.last_session_tic);
        this.board = findViewById(R.id.last_session_board);
        this.last_ini = findViewById(R.id.label_last_session_timini);
        this.last_fin = findViewById(R.id.label_last_session_timfin);
        this.last_total = findViewById(R.id.label_last_session_total);

        this.tvini = findViewById(R.id.tvTimesTampIniUsersDetails);
        this.tvfin = findViewById(R.id.tvTimesTampFinUsersDetails);
        this.tvtotal = findViewById(R.id.tvTotalTimeUsersDetails);


    }



    private void loadUserData() {

        this.userDetailsViewModel.getUser().observe(this, new Observer<UserDetails>() {
            @Override
            public void onChanged(UserDetails userDetails) {
                id = userDetails.getId();
                //Setear todos los parámetros de la UI
                username.setText(userDetails.getUsername() + " " + userDetails.getLastname());
                age.setText(userDetails.getAge().toString());
                height.setText(userDetails.getHeight());
                weight.setText(userDetails.getWeight().toString());



                if(userDetails.getGender().equals("M"))
                    gender.setText("Varón");
                else if(userDetails.getGender().equals("F"))
                    gender.setText("Mujer");

                email.setText(userDetails.getEmail());

                if(userDetails.getPhone() == 0)
                    phone.setText("");
                else
                    phone.setText(userDetails.getPhone().toString());

                if(userDetails.getPhone2() == 0)
                    phone2.setText("");
                else
                    phone2.setText(userDetails.getPhone2().toString());

                if(userDetails.getCp() == 0)
                    cp.setText("");
                else
                    cp.setText(userDetails.getCp().toString());

                city.setText(userDetails.getCity());
                address.setText(userDetails.getAddress());
                //last_monitoring.setText(userDetails.getLastMonitoring());


                if(userDetails.getSession_id() != null){

                    //Le damos valor
                    if(userDetails.isE4band())
                        e4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_green));
                    else
                        e4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_red));

                    if(userDetails.isTicwatch())
                        tic.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_green));
                    else
                        tic.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_red));

                    if(userDetails.isEhealthboard())
                        board.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_green));
                    else
                        board.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_red));

                    tvini.setText(dateFormat.format(userDetails.getTimestamp_ini()));
                    tvfin.setText(dateFormat.format(userDetails.getTimestamp_fin()));
                    tvtotal.setText(Constants.getHMS(userDetails.getTotal_time()));


                    //Añadimos visibilidad adecuada
                    tvnosession.setVisibility(View.GONE);
                    tvlabelLastSession.setVisibility(View.VISIBLE);
                    e4.setVisibility(View.VISIBLE);
                    tic.setVisibility(View.VISIBLE);
                    board.setVisibility(View.VISIBLE);
                    last_ini.setVisibility(View.VISIBLE);
                    last_fin.setVisibility(View.VISIBLE);
                    last_total.setVisibility(View.VISIBLE);
                    tvini.setVisibility(View.VISIBLE);
                    tvfin.setVisibility(View.VISIBLE);
                    tvtotal.setVisibility(View.VISIBLE);


                }else{


                   tvnosession.setVisibility(View.VISIBLE);
                   tvlabelLastSession.setVisibility(View.INVISIBLE);
                   e4.setVisibility(View.GONE);
                   tic.setVisibility(View.GONE);
                   board.setVisibility(View.GONE);
                   last_ini.setVisibility(View.GONE);
                   last_fin.setVisibility(View.GONE);
                   last_total.setVisibility(View.GONE);
                   tvini.setVisibility(View.GONE);
                   tvfin.setVisibility(View.GONE);
                   tvtotal.setVisibility(View.GONE);

                }

                //Seteamos un userDetails para tenerlo en memoria
                user = userDetails;
            }
        });

    }

    void refreshUserDetails(){
        this.userDetailsViewModel.refreshUserDetails();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back_new_monitoring:
                finish();
                break;

            case R.id.btn_new_monitoring:

                Intent i = new Intent(MyApp.getContext(), DevicesConnectionActivity.class);
                i.putExtra("id", id);
                i.putExtra("username",this.username.getText().toString());
                startActivity(i);
                break;

            case R.id.btn_edit_data:
                NewUserDialogFragment dialog = new NewUserDialogFragment(UserActionDialog.MODIFY_USER,this.user);
                dialog.show(this.getSupportFragmentManager(), "NewUserFragment");
                break;

        }
    }


}