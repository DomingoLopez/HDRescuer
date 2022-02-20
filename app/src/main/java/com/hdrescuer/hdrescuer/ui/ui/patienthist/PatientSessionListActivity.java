package com.hdrescuer.hdrescuer.ui.ui.patienthist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.common.OnSimpleDialogClick;
import com.hdrescuer.hdrescuer.common.SimpleDialogFragment;
import com.hdrescuer.hdrescuer.data.SessionsHistListViewModel;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.ui.ui.charts.SessionResultActivity;
import com.hdrescuer.hdrescuer.ui.ui.localsessions.services.UploadSessionService;
import com.hdrescuer.hdrescuer.ui.ui.patients.ListItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PatientSessionListActivity extends AppCompatActivity implements ListItemClickListener, View.OnClickListener {


    RecyclerView recyclerView;
    PatientSessionListRecyclerViewAdapter adapter;
    List<SessionEntity> sessionList;
    SessionsHistListViewModel sessionsHistListViewModel;
    ImageView btn_connection_up;

    TextView username;
    ImageView back;

    DateFormat dateFormat;
    int user_id;

    int position_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_session_list);

        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        //Obtenemos el id del usuario
        Intent i = getIntent();
       this.user_id = i.getIntExtra("user_id",0);

        //Iniciamos el dateformat
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SessionsHistListViewModel(getApplication(),user_id);
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

        this.btn_connection_up = findViewById(R.id.btn_connection_up);
        if(Constants.CONNECTION_UP.equals("SI")){
            this.btn_connection_up.setImageDrawable(getDrawable(R.drawable.ic_baseline_wifi_24_green));
        }else{
            this.btn_connection_up.setImageDrawable(getDrawable(R.drawable.ic_baseline_wifi_24_red));
        }

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
                i.putExtra("session_id",this.sessionList.get(position).getSession_id());
                i.putExtra("action","VISUALIZE");
                startActivity(i);

                break;

            case "DELETE_SESSION":
                if(!this.sessionList.get(position).isSync()) {

                    SimpleDialogFragment dialogFragment = new SimpleDialogFragment(new OnSimpleDialogClick() {
                        @Override
                        public void onPositiveButtonClick(String description) {
                        }

                        @Override
                        public void onPositiveButtonClick() {
                            sessionsHistListViewModel.deteleSessionByID(sessionList.get(position).getSession_id());
                        }

                        @Override
                        public void onNegativeButtonClick() {
                        }
                    }, "DELETE_SESSION");
                    dialogFragment.show(getSupportFragmentManager(), null);
                }else{
                    if(Constants.CONNECTION_UP.equals("SI")){
                        SimpleDialogFragment dialogFragment = new SimpleDialogFragment(new OnSimpleDialogClick() {
                            @Override
                            public void onPositiveButtonClick(String description) {
                            }

                            @Override
                            public void onPositiveButtonClick() {
                                sessionsHistListViewModel.deteleSessionByID(sessionList.get(position).getSession_id());
                            }

                            @Override
                            public void onNegativeButtonClick() {
                            }
                        }, "DELETE_SESSION");
                        dialogFragment.show(getSupportFragmentManager(), null);
                    }else{
                        Toast.makeText(this, "Conexión no disponible, vuelva a iniciar sesión", Toast.LENGTH_SHORT).show();
                    }

                }

                break;


            case "UPLOAD_SESSION":
                if(Constants.CONNECTION_UP.equals(("SI"))) {
                    this.position_selected = position;
                    Intent intent = new Intent(this.getApplicationContext(), UploadSessionService.class);
                    intent.setAction("START_UPLOAD");
                    intent.putExtra("user_id", this.user_id);
                    intent.putExtra("session_id", this.sessionList.get(position).getSession_id());
                    intent.putExtra("receiver", this.sessionResult);
                    MyApp.getInstance().startService(intent);
                }else{
                    Toast.makeText(this, "Conexión no disponible, vuelva a iniciar sesión", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public ResultReceiver sessionResult = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            //Broadcast /send the result code in intent service based on your logic(success/error) handle with switch
            switch (resultCode) {
                case 1: //Case correcto. Sesión subida

                    Toast.makeText(getApplicationContext(), "Sesión sincronizada de forma satisfactoria", Toast.LENGTH_SHORT).show();
                    int deleted_session = (int) resultData.get("deleted_session");
                    //Borramos la sesión
                    SessionEntity sesionActualizar = sessionList.get(position_selected);
                    sesionActualizar.setSync(true);
                    sessionsHistListViewModel.udpateSession(sesionActualizar);
                    //sessionsListViewModel.refreshSessions();

                    break;

                //Error al descargar a csv la sesión
                case 400:

                    break;

                //Error al subir la sesión
                case 401:
                    Toast.makeText(PatientSessionListActivity.this, "Error al subir la sesión. ¿Dispone de conexión?", Toast.LENGTH_SHORT).show();
                    break;


                //Error al subir los csv
                case 402:

                    break;

            }
        }
    };




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_details:
                finish();
                break;
        }
    }
}