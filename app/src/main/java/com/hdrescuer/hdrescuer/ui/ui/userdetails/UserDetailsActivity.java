package com.hdrescuer.hdrescuer.ui.ui.userdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.UserDetailsViewModel;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientUsersModule;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;
import com.hdrescuer.hdrescuer.common.NewUserDialogFragment;
import com.hdrescuer.hdrescuer.common.UserActionDialog;

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

    UserDetails user;

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

        this.last_monitoring = findViewById(R.id.tvLastMonitoring);
        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
        this.btn_new_monitoring = findViewById(R.id.btn_new_monitoring);
        this.btn_edit_data = findViewById(R.id.btn_edit_data);


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