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
import com.hdrescuer.hdrescuer.data.E4BandViewModel;
import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;


public class TabE4BandMonitoring extends Fragment {

    E4BandViewModel e4BandViewModel;


    TextView tvTemp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_e4_band_monitoring, container, false);
        findViews(view);

        this.e4BandViewModel = new ViewModelProvider(requireActivity()).get(E4BandViewModel.class);

        this.e4BandViewModel.getCurrentTemp().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                tvTemp.setText(aFloat.toString());
            }

        });


        // Inflate the layout for this fragment
        return view;
    }

    private void findViews(View view) {
        this.tvTemp = view.findViewById(R.id.tab_e4band_temp);
    }
}