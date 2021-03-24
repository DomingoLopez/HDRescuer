package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


public class GlobalMonitoringViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    private int user_id;

    public GlobalMonitoringViewModel(@NonNull Application application, int id) {
        super(application);
        this.user_id = id;
        //Iniciamos un nuevo GlobalMonitoringRepository

    }


    public int getUser() {

        return this.user_id;
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}