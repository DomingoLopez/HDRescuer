package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


public class UserDetailsViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    public UserDetailsRepository userDetailsRepository;
    public LiveData<UserDetails> userDetails;

    public UserDetailsViewModel(@NonNull Application application, int id) {
        super(application);
        this.userDetailsRepository = new UserDetailsRepository(id);
        this.userDetails = this.userDetailsRepository.getUser(id);

    }


    public LiveData<UserDetails> getUser() {
        return this.userDetails;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}