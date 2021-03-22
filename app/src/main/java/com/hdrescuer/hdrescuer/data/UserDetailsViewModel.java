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
    public MutableLiveData<UserDetails> userDetails;

    public UserDetailsViewModel(@NonNull Application application, int id) {
        super(application);
        this.userDetailsRepository = new UserDetailsRepository(id);
        this.userDetails = getUser(id);

    }


    public MutableLiveData<UserDetails> getUser(int id) {

        return this.userDetailsRepository.getUser(id);
    }

    public UserDetailsRepository getRepo(){
        return this.userDetailsRepository;
    }

    public void updateUserDetails(UserDetails userDetails){
        this.userDetailsRepository.updateUser(userDetails);
    }

    public void refreshUserDetails(int id){
        this.userDetails = getUser(id);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}