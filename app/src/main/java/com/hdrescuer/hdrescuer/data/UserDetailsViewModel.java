package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserInfo;


public class UserDetailsViewModel extends AndroidViewModel implements ViewModelProvider.Factory {


    public UserDetailsRepository userDetailsRepository;
    String user_id;

    public UserDetailsViewModel(@NonNull Application application, String id) {
        super(application);
        this.user_id = id;
        this.userDetailsRepository = new UserDetailsRepository(id);

    }


    public MutableLiveData<UserDetails> getUser() {

        return this.userDetailsRepository.getUser();
    }

    public void updateUserDetails(UserInfo userInfo){
        this.userDetailsRepository.updateUser(userInfo);
    }

    public void refreshUserDetails(){
        this.userDetailsRepository.refreshUserDetails(this.user_id);
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

}