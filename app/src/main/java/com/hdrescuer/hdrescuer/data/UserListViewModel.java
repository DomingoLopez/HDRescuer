package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private UserListRepository userListRepository;
    public MutableLiveData<List<User>> users;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userListRepository = new UserListRepository();
        this.users = getUsers();
    }

    public UserListRepository getRepo(){
        return this.userListRepository;
    }

    public MutableLiveData<List<User>> getUsers(){
        return this.userListRepository.getAllUsers();
    }

    public void setNewUser(UserDetails userDetails){
        this.userListRepository.setNewUser(userDetails);
    }

    public void refreshUsers(){
        this.users = getUsers();
    }

}
