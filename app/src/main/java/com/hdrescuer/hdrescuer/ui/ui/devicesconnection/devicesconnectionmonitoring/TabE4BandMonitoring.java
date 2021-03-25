package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;


public class TabE4BandMonitoring extends Fragment {

    GlobalMonitoringViewModel globalMonitoringViewModel;


    TextView tvTemp;
    //TextView tvBattery;
    TextView tvAccX;
    TextView tvAccY;
    TextView tvAccZ;
    TextView tvBVP;
    TextView tvHR;
    TextView tvGSR;
    TextView tvIBI;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_e4_band_monitoring, container, false);
        findViews(view);

        this.globalMonitoringViewModel = new ViewModelProvider(requireActivity()).get(GlobalMonitoringViewModel.class);

        createObserverForViewModel();
        // Inflate the layout for this fragment
        return view;
    }


    private void findViews(View view) {
        this.tvTemp = view.findViewById(R.id.tvTemp);
        //this.tvBattery = view.findViewById(R.id.tvBattery);
        this.tvAccX = view.findViewById(R.id.tvWatchAccx);
        this.tvAccY = view.findViewById(R.id.tvWatchAccy);
        this.tvAccZ = view.findViewById(R.id.tvWatchAccz);
        this.tvBVP = view.findViewById(R.id.tvBVP);
        this.tvGSR = view.findViewById(R.id.tvGSR);
        this.tvIBI = view.findViewById(R.id.tvIBI);
        this.tvHR = view.findViewById(R.id.tvHR);

    }


    //Creamos los observers para el viewmodel. Habrá que implementar el envío del dato al servidor
    //En el mismo cambio, para que sea en tiempo real.
    private void createObserverForViewModel() {

        //Observer de temperatura
        this.globalMonitoringViewModel.getCurrentTemp().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvTemp.setText(aFloat.toString());
            }
        });


        //Observer de GSR
        this.globalMonitoringViewModel.getCurrentGsr().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvGSR.setText(aFloat.toString());
            }
        });
        //Observer de HR
        this.globalMonitoringViewModel.getCurrentHr().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvHR.setText(aFloat.toString());
            }
        });
        //Observer de IBI
        this.globalMonitoringViewModel.getCurrentIbi().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvIBI.setText(aFloat.toString());
            }
        });
        //Observer de AccX
        this.globalMonitoringViewModel.getCurrentAccX().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccX.setText(aFloat.toString());
            }
        });
        //Observer de AccY
        this.globalMonitoringViewModel.getCurrentAccY().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccY.setText(aFloat.toString());
            }
        });
        //Observer de AccZ
        this.globalMonitoringViewModel.getCurrentAccZ().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccZ.setText(aFloat.toString());
            }
        });
        //Observer de BVP
        this.globalMonitoringViewModel.getCurrentBvp().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvBVP.setText(aFloat.toString());
            }
        });
    }



}