package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.List;

/**
 * ViewModel de la lista de usuarios
 * @author Domingo Lopez
 */
public class UserListViewModel extends AndroidViewModel {

    private UserListRepository userListRepository;

    /**
     * Constructor
     * @author Domingo Lopez
     * @param application
     */
    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userListRepository = new UserListRepository();
    }


    /**
     * Método que recibe los usuarios desde el repositorio
     * @author Domingo Lopez
     * @return MutableLiveData
     */
    public MutableLiveData<List<User>> getUsers(){
        return this.userListRepository.getUsers();
    }

    /**
     * Método que le dice al repositorio que cree un nuevo usuario
     * @author Domingo Lopez
     * @param userEntity
     */
    public void setNewUser(UserEntity userEntity){
        this.userListRepository.setNewUser(userEntity);
    }

    /**
     * Método que refrescha los usuarios
     * @author Domingo Lopez
     */
    public void refreshUsers(){
        this.userListRepository.refreshUsers();
    }


}
