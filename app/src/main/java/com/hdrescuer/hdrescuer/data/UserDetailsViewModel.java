package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;


public class UserDetailsViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    public UserDetailsRepository userDetailsRepository;

    public UserDetailsViewModel(@NonNull Application application, String id) {
        super(application);
        this.userDetailsRepository = new UserDetailsRepository(id);

    }


    public MutableLiveData<UserDetails> getUser() {

        return this.userDetailsRepository.getUser();
    }

    public void updateUserDetails(UserDetails userDetails){
        this.userDetailsRepository.updateUser(userDetails);
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}