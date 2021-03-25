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
        this.tvhb = view.findViewById(R.id.tvhb);
        this.tvsteps = view.findViewById(R.id.tvsteps);
    }

    private void createObserverForViewModel() {
        //Observers de Acc
        this.globalMonitoringViewModel.getAccx().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAccx.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getAccy().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAccy.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getAccz().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAccz.setText(df2.format(aFloat));
            }
        });

        //Observers de Accl
        this.globalMonitoringViewModel.getAcclx().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAcclx.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getAccly().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAccly.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getAcclz().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvAcclz.setText(df2.format(aFloat));
            }
        });

        //Observers de Giroscopio
        this.globalMonitoringViewModel.getGirx().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvgirx.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getGiry().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvgiry.setText(df2.format(aFloat));
            }
        });
        this.globalMonitoringViewModel.getGirz().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvgirz.setText(df2.format(aFloat));
            }
        });

        //Observers de hrppg
        this.globalMonitoringViewModel.getHrppg().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppg.setText(aFloat.toString());
            }
        });

        //Observers de hrppgraw
        this.globalMonitoringViewModel.getHrppgraw().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppgraw.setText(aFloat.toString());
            }
        });

        //Observers de hrppgraw
        this.globalMonitoringViewModel.getHb().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhb.setText(aFloat.toString());
            }
        });

        //Observers de step
        this.globalMonitoringViewModel.getStep().observe(requireActivity(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvsteps.setText(aFloat.toString());
            }
        });

    }


}