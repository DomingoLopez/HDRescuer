package com.hdrescuer.hdrescuer.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;

/**
 * ViewModel de la lista de usuarios
 * @author Domingo Lopez
 */
public class PatientListViewModel extends AndroidViewModel {

    private PatientListRepository patientListRepository;

    /**
     * Constructor
     * @author Domingo Lopez
     * @param application
     */
    public PatientListViewModel(@NonNull Application application) {
        super(application);
        this.patientListRepository = new PatientListRepository();
    }


    /**
     * Método que recibe los usuarios desde el repositorio
     * @author Domingo Lopez
     * @return MutableLiveData
     */
    public MutableLiveData<List<User>> getPatients(){
        return this.patientListRepository.getPatients();
    }

    /**
     * Método que le dice al repositorio que cree un nuevo usuario
     * @author Domingo Lopez
     * @param userEntity
     */
    public void setNewPatient(UserEntity userEntity){
        this.patientListRepository.setNewPatient(userEntity);
    }

    /**
     * Método que refrescha los usuarios
     * @author Domingo Lopez
     */
    public void refreshPatients(){
        this.patientListRepository.refreshPatients();
    }


}
