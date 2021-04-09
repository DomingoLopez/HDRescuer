package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.E4BandRepository;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.EhealthBoardService;
import com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services.SampleRateFilterThread;


public class DevicesMonitoringFragment extends Fragment implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Button btnStopMonitor;

    DataClient dataclient;
    PutDataMapRequest putDataMapRequestStop;
    PutDataRequest putDataReqStop;


    public DevicesMonitoringFragment() {
        // Required empty public constructor
    }

    public DevicesMonitoringFragment(DataClient dataClient, PutDataMapRequest stopMapRequest, PutDataRequest stopRequest){
        this.dataclient = dataClient;
        this.putDataMapRequestStop = stopMapRequest;
        this.putDataReqStop = stopRequest;
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

    private void findViews(View view) {
        this.tabLayout = view.findViewById(R.id.tabLayout);
        this.viewPager = view.findViewById(R.id.view_pager_monitoring);
        this.btnStopMonitor = view.findViewById(R.id.btn_stop_monitoring);
        this.btnStopMonitor.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        String timeback = String.valueOf(System.currentTimeMillis());
        this.putDataMapRequestStop.getDataMap().putString("MONITORINGSTOP", timeback);
        this.putDataReqStop = this.putDataMapRequestStop.asPutDataRequest();
        Task<DataItem> putDataTask1 = this.dataclient.putDataItem(this.putDataReqStop);
        putDataTask1.addOnCompleteListener(new OnCompleteListener<DataItem>() {
            @Override
            public void onComplete(@NonNull Task<DataItem> task) {
                Log.i("INFOTASK", "PUESTO VALOR STOP MONITORING EN DATACLIENT");
            }
        });
        SampleRateFilterThread.STATUS = "INACTIVO";
        EhealthBoardService.STATUS = "INACTIVO";

        getActivity().finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String timeback = String.valueOf(System.currentTimeMillis());
        this.putDataMapRequestStop.getDataMap().putString("MONITORINGSTOP", timeback);
        this.putDataReqStop = this.putDataMapRequestStop.asPutDataRequest();
        Task<DataItem> putDataTask1 = this.dataclient.putDataItem(this.putDataReqStop);
        putDataTask1.addOnCompleteListener(new OnCompleteListener<DataItem>() {
            @Override
            public void onComplete(@NonNull Task<DataItem> task) {
                Log.i("INFOTASK", "PUESTO VALOR STOP MONITORING EN DATACLIENT");
            }
        });
        SampleRateFilterThread.STATUS = "INACTIVO";
        EhealthBoardService.STATUS = "INACTIVO";


    }
}