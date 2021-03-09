package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


public class DevicesConnectionViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    public DevicesConnectionRepository devicesConnectionRepository;
    public LiveData<UserDetails> userDetails;

    public DevicesConnectionViewModel(@NonNull Application application, int id) {
        super(application);
        this.devicesConnectionRepository = new DevicesConnectionRepository();


    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}