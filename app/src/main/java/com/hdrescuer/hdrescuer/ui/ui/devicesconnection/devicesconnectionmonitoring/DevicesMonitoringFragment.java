package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.EhealthBoardThread;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.SampleRateFilterThread;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.StartStopSessionService;

import java.time.Clock;

/**
 * Fragmento que contiene a los Fragments TabE4BandMonitoring, TabOximeterMonitoring, TabWatchMonitoring.
 * Hace uso del ViewPager y su adapter
 * @author Domingo Lopez
 */
public class DevicesMonitoringFragment extends Fragment implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Button btnStopMonitor;
    int session_id;
    ResultReceiver receiver;


    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public DevicesMonitoringFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor con parámetros
     * @author Domingo Lopez
     * @param session_id
     * @param receiver
     */
    public DevicesMonitoringFragment(int session_id, ResultReceiver receiver){
        this.session_id = session_id;
        this.receiver = receiver;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_devices_monitoring, container, false);

         findViews(view);


        return view;
    }

    /**
     * Método onViewCreated. Cuando se crea la vista utiliza un TabLayoutMediator para elegir entre los distintos fragmentos de monitorización
     * @author Domingo Lopez
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewPager.setAdapter(new ViewPagerAdapter(this.getActivity()));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                this.tabLayout, this.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0:
                        tab.setText("E4Band");
                        break;
                    case 1:
                        tab.setText("Tic Watch Pro");
                        break;
                    case 2:
                        tab.setText("Oxímetro");
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();


    }

    /**
     * Método que inicia las vistas
     * @author Domingo Lopez
     * @param view
     */
    private void findViews(View view) {
        this.tabLayout = view.findViewById(R.id.tabLayout);
        this.viewPager = view.findViewById(R.id.view_pager_monitoring);
        this.btnStopMonitor = view.findViewById(R.id.btn_stop_monitoring);
        this.btnStopMonitor.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        stopSession();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * Método que solicita la parada de la sesión mandando un Intent al IntentService StartStopSessionService
     * @author Domingo Lopez
     */
    private void stopSession() {
        //Paramos las hebras que pudiera haber activas
        SampleRateFilterThread.STATUS = "INACTIVO";
        EhealthBoardThread.STATUS = "INACTIVO";

        Intent intent = new Intent(this.getActivity(), StartStopSessionService.class);

        if(Constants.CONNECTION_MODE=="STREAMING" && Constants.CONNECTION_UP.equals("SI")){
            intent.setAction("STOP_SESSION");
        }else{
            intent.setAction("STOP_OFFLINE_MODE");
        }
        String instant = Clock.systemUTC().instant().toString();
        intent.putExtra("session_id",this.session_id);
        intent.putExtra("timestamp_fin",instant);
        intent.putExtra("receiver",this.receiver);

        this.getActivity().startService(intent);

    }




}