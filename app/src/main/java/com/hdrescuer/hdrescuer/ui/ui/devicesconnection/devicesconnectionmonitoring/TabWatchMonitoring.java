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
import com.hdrescuer.hdrescuer.data.E4BandViewModel;
import com.hdrescuer.hdrescuer.data.TicWatchViewModel;


public class TabWatchMonitoring extends Fragment {

    TicWatchViewModel ticWatchViewModel;

//    TextView tvAccx;
//    TextView tvAccy;
//    TextView tvAccz;
//    TextView tvAcclx;
//    TextView tvAccly;
//    TextView tvAcclz;
//    TextView tvgirx;
//    TextView tvgiry;
//    TextView tvgirz;
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

        this.ticWatchViewModel = new ViewModelProvider(requireActivity()).get(TicWatchViewModel.class);

        createObserverForViewModel();
        // Inflate the layout for this fragment
        return view;
    }

    private void findViews(View view) {

//        this.tvAccx = view.findViewById(R.id.tvWatchAccx);
//        this.tvAccy = view.findViewById(R.id.tvWatchAccy);
//        this.tvAccz = view.findViewById(R.id.tvWatchAccz);
//
//        this.tvAcclx = view.findViewById(R.id.tvAcclx);
//        this.tvAccly = view.findViewById(R.id.tvAccly);
//        this.tvAcclz = view.findViewById(R.id.tvAcclz);
//
//        this.tvgirx = view.findViewById(R.id.tvgirx);
//        this.tvgiry = view.findViewById(R.id.tvgiry);
//        this.tvgirz = view.findViewById(R.id.tvgirz);

        this.tvhrppg = view.findViewById(R.id.tvhrppg);
        this.tvhrppgraw = view.findViewById(R.id.tvhrppgraw);
        this.tvhb = view.findViewById(R.id.tvhb);
        this.tvsteps = view.findViewById(R.id.tvsteps);
    }

    private void createObserverForViewModel() {
        //Observers de Acc
//        this.ticWatchViewModel.getAccx().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAccx.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getAccy().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAccy.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getAccz().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAccz.setText(aFloat.toString());
//            }
//        });
//
//        //Observers de Accl
//        this.ticWatchViewModel.getAcclx().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAcclx.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getAccly().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAccly.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getAcclz().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvAcclz.setText(aFloat.toString());
//            }
//        });
//
//        //Observers de Giroscopio
//        this.ticWatchViewModel.getGirx().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvgirx.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getGiry().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvgiry.setText(aFloat.toString());
//            }
//        });
//        this.ticWatchViewModel.getGirz().observe(getViewLifecycleOwner(), new Observer<Float>() {
//            @Override
//            public void onChanged(Float aFloat) {
//                tvgirz.setText(aFloat.toString());
//            }
//        });

        //Observers de hrppg
        this.ticWatchViewModel.getHrppg().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppg.setText(aFloat.toString());
            }
        });

        //Observers de hrppgraw
        this.ticWatchViewModel.getHrppgraw().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhrppgraw.setText(aFloat.toString());
            }
        });

        //Observers de hrppgraw
        this.ticWatchViewModel.getHb().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvhb.setText(aFloat.toString());
            }
        });

        //Observers de step
        this.ticWatchViewModel.getStep().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvsteps.setText(aFloat.toString());
            }
        });

    }


}