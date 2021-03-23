package com.hdrescuer.hdrescuer.ui.ui.userdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.UserDetailsViewModel;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;
import com.hdrescuer.hdrescuer.common.NewUserDialogFragment;
import com.hdrescuer.hdrescuer.common.UserActionDialog;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {



    //Servicio de Login y ConectionClient
    AuthConectionClient authConectionClient;
    AuthApiService authApiService;
    //ViewModel
    UserDetailsViewModel userDetailsViewModel;
    //Parámetros de la ficha del usuario
    int id;
    TextView username;
    TextView height;
    TextView weight;
    TextView age;
    TextView gender;
    TextView email;
    TextView phone;
    TextView last_monitoring;
    ImageView btn_back;
    Button btn_new_monitoring;
    Button btn_edit_data;

    UserDetails user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);

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
        this.last_monitoring = findViewById(R.id.tvLastMonitoring);
        this.btn_back = findViewById(R.id.btn_back_new_monitoring);
        this.btn_new_monitoring = findViewById(R.id.btn_new_monitoring);
        this.btn_edit_data = findViewById(R.id.btn_edit_data);

    }


    private void loadUserData() {

        this.userDetailsViewModel.userDetails.observe(this, new Observer<UserDetails>() {
            @Override
            public void onChanged(UserDetails userDetails) {
                id = userDetails.getId();
                //Setear todos los parámetros de la UI
                username.setText(userDetails.getUsername() + " " + userDetails.getLastName());
                age.setText(userDetails.getAge().toString());
                height.setText(userDetails.getHeight().toString());
                weight.setText(userDetails.getWeight().toString());

                if(userDetails.getGender().equals("M"))
                    gender.setText("Varón");
                else if(userDetails.getGender().equals("F"))
                    gender.setText("Mujer");

                email.setText(userDetails.getEmail());
                phone.setText(userDetails.getPhone().toString());
                last_monitoring.setText(userDetails.getLastMonitoring());

                //Seteamos un userDetails para tenerlo en memoria
                user = userDetails;

            }
        });


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


    @Override
    protected void onResume() {
        super.onResume();
        this.userDetailsViewModel.refreshUserDetails(this.user.getId());
    }
}