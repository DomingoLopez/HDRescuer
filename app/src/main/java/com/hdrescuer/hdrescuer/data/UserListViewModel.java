package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private UserListRepository userListRepository;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userListRepository = new UserListRepository();
    }


    public MutableLiveData<List<User>> getUsers(){
        return this.userListRepository.getUsers();
    }

    public void setNewUser(UserDetails userInfo){
        this.userListRepository.setNewUser(userInfo);
    }

    public void refreshUsers(){
        this.userListRepository.refreshUsers();
    }


}
