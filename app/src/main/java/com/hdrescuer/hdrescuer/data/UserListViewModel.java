package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private UserListRepository userListRepository;
    private LiveData<List<User>> users;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userListRepository = new UserListRepository();
        this.users = this.userListRepository.getAllUsers();
    }


    public LiveData<List<User>> getUsers(){
        return this.users;
    }


}
