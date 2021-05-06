package com.hdrescuer.hdrescuer.ui.ui.localsessions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.SessionsListViewModel;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.ui.ui.charts.SessionResultActivity;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.DevicesConnectionActivity;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring.DevicesMonitoringFragment;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.EhealthBoardThread;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.SampleRateFilterThread;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.StartStopSessionService;
import com.hdrescuer.hdrescuer.ui.ui.localsessions.services.UploadSessionService;
import com.hdrescuer.hdrescuer.ui.ui.users.ListItemClickListener;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocalSessionsFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    MySessionsRecyclerViewAdapter adapter;
    List<SessionEntity> sessionList;
    List<User> users;
    SessionsListViewModel sessionsListViewModel;
    UserListViewModel userListViewModel;

    public Map<String, String> usuarios_predictivo = new HashMap<String,String>();

    boolean alreadyCreated = false;


    public LocalSessionsFragment() {

    }


    @SuppressWarnings("unused")
    public static LocalSessionsFragment newInstance(int columnCount) {
        LocalSessionsFragment fragment = new LocalSessionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        //Obtenemos ViewModel de las sesiones
        this.sessionsListViewModel = new ViewModelProvider(requireActivity()).get(SessionsListViewModel.class);

        //Obtenemos viewmodel de los usuarios descargados
        this.userListViewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);

        alreadyCreated = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sessions_list, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list_sessions);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.users = this.userListViewModel.getUsers().getValue();
        setUsersAElegir();

        this.adapter = new MySessionsRecyclerViewAdapter(
                getActivity(),
                this.sessionList,
                this.users,
                this
        );
        this.recyclerView.setAdapter(adapter);


        findViews(view);
        loadSessionsData();

        return view;
    }

    void setUsersAElegir(){
        if(this.users != null){
            for(int i = 0; i< this.users.size(); i++){
                this.usuarios_predictivo.put(this.users.get(i).getLastname()+", "+this.users.get(i).getUsername(), this.users.get(i).getId());
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if(!alreadyCreated){
/*
            refreshSessions();
*/
        }
        alreadyCreated = false;
    }


/*
    private void refreshSessions() {
        this.sessionsListViewModel.refreshSessions();
    }
*/


    /**
     * Método que busca los elementos de la vista
     * @author Domingo Lopez
     * @param view
     */
    private void findViews(View view) {

    }


    /**
     * Método que añade observer al viewmodel para observar cambios en la lista de usuarios
     * @author Domingo Lopez
     */
    private void loadSessionsData() {

        this.sessionsListViewModel.getSessions().observe(getActivity(), new Observer<List<SessionEntity>>() {
            @Override
            public void onChanged(List<SessionEntity> sessions) {

                sessionList = sessions;
                adapter.setData(sessionList);
            }
        });





    }

    /**
     * Método llamado al clickar en un item de la lista. Es un método de la clase implementada ListItemClickListener
     * @author Domingo Lopez
     * @param position
     */
    @Override
    public void onListItemClick(int position) {


    }

    @Override
    public void onListItemClickUser(int position, String user_elegido){

        if(user_elegido == null || user_elegido == ""){
            Toast.makeText(requireActivity(), "No ha seleccionado un paciente para la sesión", Toast.LENGTH_SHORT).show();
            return;
        }

        //Si el usuario contiene algo, lo comparamos con el Map que tenemos
        String user_id = this.usuarios_predictivo.get(user_elegido);
        if(user_id != null){
            int id_session_local = this.sessionList.get(position).id_session_local;

            Intent intent = new Intent(this.getContext(), UploadSessionService.class);
            intent.setAction("START_UPLOAD");
            intent.putExtra("user_id",user_id);
            intent.putExtra("id_session_local", id_session_local);
            intent.putExtra("receiver",this.sessionResult);
            MyApp.getInstance().startService(intent);

        }else{
            Toast.makeText(requireActivity(), "Debe escribir el nombre del paciente al que pertenece la sesión", Toast.LENGTH_SHORT).show();
        }

    }


    public ResultReceiver sessionResult = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            //Broadcast /send the result code in intent service based on your logic(success/error) handle with switch
            switch (resultCode) {
                case 1: //Case correcto. Sesión subida

                    Toast.makeText(requireActivity(), "Sesión sincronizada de forma satisfactoria", Toast.LENGTH_SHORT).show();
                    int deleted_session = (int) resultData.get("deleted_session");
                    //Borramos la sesión
                    sessionsListViewModel.deteleSessionByID(deleted_session);

                    break;

                //Error al descargar a csv la sesión
                case 400:

                    break;

                //Error al subir la sesión
                case 401:

                    break;


                //Error al subir los csv
                case 402:

                    break;

            }
        }
    };



        @Override
    public void onClick(View view) {

    }


}