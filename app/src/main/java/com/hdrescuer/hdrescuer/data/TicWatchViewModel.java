package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.empatica.empalink.delegate.EmpaDataDelegate;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TicWatchViewModel extends AndroidViewModel implements ViewModelProvider.Factory{


    private int user_id ;

    private final static double timezoneOffset = TimeZone.getDefault().getRawOffset() / 1000d;


    private MutableLiveData<Float> param1;
    private MutableLiveData<Double> param2;




    private ScheduledExecutorService scheduler;
    private float averageHr = 0;


    public TicWatchViewModel(@NonNull Application application, int id) {
        super(application);
        this.user_id = id;


        param1 = new MutableLiveData<>();
        param2 = new MutableLiveData<>();



    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }




    /****************GETTERS*********************
     *
     *
     ********************************************/








}