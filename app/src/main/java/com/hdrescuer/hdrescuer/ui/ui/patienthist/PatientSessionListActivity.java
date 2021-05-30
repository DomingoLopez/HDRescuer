package com.hdrescuer.hdrescuer.ui.ui.patienthist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.OnSimpleDialogClick;
import com.hdrescuer.hdrescuer.common.SimpleDialogFragment;
import com.hdrescuer.hdrescuer.data.SessionsHistListViewModel;
import com.hdrescuer.hdrescuer.data.SessionsListViewModel;
import com.hdrescuer.hdrescuer.data.UserDetailsViewModel;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.ui.ui.charts.SessionResultActivity;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;
import com.hdrescuer.hdrescuer.ui.ui.localsessions.MySessionsRecyclerViewAdapter;
import com.hdrescuer.hdrescuer.ui.ui.users.ListItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientSessionListActivity extends AppCompatActivity implements ListItemClickListener, View.OnClickListener {


    RecyclerView recyclerView;
    PatientSessionListRecyclerViewAdapter adapter;
    List<SessionEntity> sessionList;
    SessionsHistListViewModel sessionsHistListViewModel;

    TextView username;
    ImageView back;

    DateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_session_list);

        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
        String id = i.getStringExtra("id");

        //Iniciamos el dateformat
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SessionsHistListViewModel(getApplication(),id);
            }
        };

        this.sessionsHistListViewModel = new ViewModelProvider(this,factory).get(SessionsHistListViewModel.class);

        findViews();
        loadSessionHistData();

    }





    private void findViews() {

        this.username = findViewById(R.id.userNameToolbarHist);
        this.username.setText("Histórico de sesiones");
        this.back = findViewById(R.id.btn_back_details);
        this.back.setOnClickListener(this);

        this.recyclerView = findViewById(R.id.list_sessions_hist);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new PatientSessionListRecyclerViewAdapter(
                this,
                this.sessionList,
                this
        );
        this.recyclerView.setAdapter(adapter);

    }



    private void loadSessionHistData() {

        this.sessionsHistListViewModel.getSessions().observe(this, new Observer<List<SessionEntity>>() {
            @Override
            public void onChanged(List<SessionEntity> sessions) {
                sessionList = sessions;
                adapter.setData(sessionList);
            }
        });
    }

    @Override
    public void onListItemClick(int position) {


    }

    @Override
    public void onListItemClickUser(int position, String user) {

        switch (user){

            case "SHOW_RESULTS":

                Intent i = new Intent(PatientSessionListActivity.this, SessionResultActivity.class);
                i.putExtra("id_session_local",this.sessionList.get(position).getId_session_local());
                i.putExtra("action","VISUALIZE");
                startActivity(i);

                break;

            case "DELETE_SESSION":

                SimpleDialogFragment dialogFragment = new SimpleDialogFragment(new OnSimpleDialogClick() {
                    @Override
                    public void onPositiveButtonClick(String description) {

                    }

                    @Override
                    public void onPositiveButtonClick() {

                        sessionsHistListViewModel.deteleSessionByID(sessionList.get(position).getId_session_local());
                    }

                    @Override
                    public void onNegativeButtonClick() {


                    }
                }, "DELETE_SESSION");

                dialogFragment.show(getSupportFragmentManager(), null);



                break;
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_details:
                finish();
                break;
        }
    }
}