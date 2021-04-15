package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.devicesconnectionmonitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.GlobalMonitoringViewModel;

import java.text.DecimalFormat;


public class TabOximeterMonitoring extends Fragment {

    GlobalMonitoringViewModel globalMonitoringViewModel;


    TextView tvbpm;
    TextView tvo2;
    TextView tvair;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tab_oximeter_monitoring, container, false);
        findViews(view);

        this.globalMonitoringViewModel = new ViewModelProvider(requireActivity()).get(GlobalMonitoringViewModel.class);

        createObserverForViewModel();
        // Inflate the layout for this fragment
        return view;

    }





    void findViews(View view){
        this.tvbpm = view.findViewById(R.id.bpm_oxi_value);
        this.tvo2 = view.findViewById(R.id.o2_oxi_value);
        this.tvair = view.findViewById(R.id.oxi_air);
    }


    void createObserverForViewModel(){
        //Observer de bpm
        this.globalMonitoringViewModel.getOxi_bpm().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer bpm) {
                tvbpm.setText(bpm.toString());
            }
        });


        //Observer de GSR
        this.globalMonitoringViewModel.getOxi_o2().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer o2) {
                tvo2.setText(o2.toString());
            }
        });

        //Observer de GSR
        this.globalMonitoringViewModel.getOxi_air().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer air) {
                tvair.setText(air.toString());
            }
        });
    }
}