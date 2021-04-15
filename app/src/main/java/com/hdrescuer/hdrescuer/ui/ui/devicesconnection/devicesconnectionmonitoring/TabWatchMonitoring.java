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


public class TabWatchMonitoring extends Fragment {

    GlobalMonitoringViewModel globalMonitoringViewModel;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    TextView tvAccx;
    TextView tvAccy;
    TextView tvAccz;
    TextView tvAcclx;
    TextView tvAccly;
    TextView tvAcclz;
    TextView tvgirx;
    TextView tvgiry;
    TextView tvgirz;
    TextView tvhrppg;
    TextView tvhrppgraw;
    TextView tvsteps;
    TextView tvhb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_watch_monitoring, container, false);
        findViews(view);

        this.globalMonitoringViewModel = new ViewModelProvider(requireActivity()).get(GlobalMonitoringViewModel.class);

        createObserverForViewModel();
        // Inflate the layout for this fragment
        return view;
    }

    private void findViews(View view) {

        this.tvAccx = view.findViewById(R.id.tvAccxWatch);
        this.tvAccy = view.findViewById(R.id.tvAccyWatch);
        this.tvAccz = view.findViewById(R.id.tvAcczWatch);

        this.tvAcclx = view.findViewById(R.id.tvAcclxWatch);
        this.tvAccly = view.findViewById(R.id.tvAcclyWatch);
        this.tvAcclz = view.findViewById(R.id.tvAcclzWatch);

        this.tvgirx = view.findViewById(R.id.tvGirxWatch);
        this.tvgiry = view.findViewById(R.id.tvGiryWatch);
        this.tvgirz = view.findViewById(R.id.tvGirzWatch);

        this.tvhrppg = view.findViewById(R.id.tvhrppg);
        this.tvhrppgraw = view.findViewById(R.id.tvhrppgraw);
        this.tvsteps = view.findViewById(R.id.tvsteps);
    }

    private void createObserverForViewModel() {
        //Observers de Acc
        this.globalMonitoringViewModel.getAccx().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccx.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getAccy().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccy.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getAccz().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccz.setText(aFloat.toString());
            }
        });

        //Observers de Accl
        this.globalMonitoringViewModel.getAcclx().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAcclx.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getAccly().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAccly.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getAcclz().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvAcclz.setText(aFloat.toString());
            }
        });

        //Observers de Giroscopio
        this.globalMonitoringViewModel.getGirx().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvgirx.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getGiry().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvgiry.setText(aFloat.toString());
            }
        });
        this.globalMonitoringViewModel.getGirz().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aFloat) {
                tvgirz.setText(aFloat.toString());
            }
        });

        //Observers de hrppg
        this.globalMonitoringViewModel.getHrppg().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppg.setText(String.valueOf(Math.round(aFloat)));
            }
        });

        //Observers de hrppgraw
        this.globalMonitoringViewModel.getHrppgraw().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppgraw.setText(String.valueOf(Math.round(aFloat)));
            }
        });



        //Observers de step
        this.globalMonitoringViewModel.getStep().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer inte) {
                tvsteps.setText(inte.toString());
            }
        });

    }


}