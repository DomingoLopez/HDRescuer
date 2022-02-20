package com.hdrescuer.hdrescuer.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.PatientDetailsViewModel;
import com.hdrescuer.hdrescuer.data.PatientListViewModel;
import com.hdrescuer.hdrescuer.db.entity.UserEntity;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;

import java.time.Clock;

/**
 * Clase dentro del paquete common, ya que sirve para el alta y modificación de un usuario.
 * @author Domingo Lopez
 */
public class NewPatientDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText edName;
    EditText edLastName;
    EditText edEmail;
    RadioGroup GenderGroup;
    RadioButton rbmale;
    RadioButton rbfemale;
    EditText numAge;
    EditText numHeight;
    EditText numWeight;
    EditText numPhone;
    ImageButton add_user_btn;
    ImageButton close_btn;
    EditText numPhone2;
    EditText edAddress;
    EditText edciudad;
    EditText numcp;

    UserActionDialog type;
    UserEntity userEntity;

    //Servicio de api
    AuthConectionClient authConectionClient;
    AuthApiService authApiService;

    //Servicio de la BD para usuarios

    /**
     * Constructor del diálogo
     * @param type
     * @param userEntity
     */
    public NewPatientDialogFragment(UserActionDialog type, UserEntity userEntity){
        this.type = type;
        this.userEntity = userEntity;
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_HDRescuer_FullScreenDialogStyle);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_new_user_dialog,container, false);

        findViews(view);

        setFormValues();

        return view;
    }

    /**
     * Método que inicia los elementos de la vista
     * @author Domingo Lopez
     * @param view
     */
    private void findViews(View view) {

        this.edName = view.findViewById(R.id.txtName);
        this.edLastName = view.findViewById(R.id.txtlastName);
        this.edEmail = view.findViewById(R.id.txtEmail);
        this.GenderGroup = view.findViewById(R.id.rgGender);
        this.rbmale = view.findViewById(R.id.rbVaron);
        this.rbfemale = view.findViewById(R.id.rbMujer);
        this.numAge = view.findViewById(R.id.numAge);
        this.numHeight = view.findViewById(R.id.numHeight);
        this.numWeight = view.findViewById(R.id.numWeight);
        this.numPhone = view.findViewById(R.id.numPhone);
        this.numPhone2 = view.findViewById(R.id.numPhone2);
        this.edAddress = view.findViewById(R.id.txtAddress);
        this.edciudad = view.findViewById(R.id.txtCity);
        this.numcp = view.findViewById(R.id.numcp);
        this.add_user_btn = view.findViewById(R.id.btnAddUser);
        this.close_btn = view.findViewById(R.id.btnClose);

        this.add_user_btn.setOnClickListener(this);
        this.close_btn.setOnClickListener(this);

    }

    /**
     * Método que setea los elementos del formulario del diálogo
     * @author Domingo Lopez
     */
    private void setFormValues() {
        switch (this.type){

            case NEW_USER:
                //Diálogo de nuevo usuario
                this.edName.setText("");
                this.edLastName.setText("");
                this.edEmail.setText("");
                this.GenderGroup.clearCheck();
                this.numAge.setText("");
                this.numHeight.setText("");
                this.numWeight.setText("");
                this.numPhone.setText("");
                this.numPhone2.setText("");
                this.edAddress.setText("");
                this.edciudad.setText("");;
                this.numcp.setText("");

                break;

            case MODIFY_USER:
                //Diálogo para modificar el usuario
                this.edName.setText(this.userEntity.getUsername());
                this.edLastName.setText(this.userEntity.getLastname());
                this.edEmail.setText(this.userEntity.getEmail());

                if(this.userEntity.getPhone2() == "")
                    this.numPhone2.setText("");
                else
                    this.numPhone2.setText(this.userEntity.getPhone2());

                if(this.userEntity.getPhone() == "")
                    this.numPhone.setText("");
                else
                    this.numPhone.setText(this.userEntity.getPhone());

                if(this.userEntity.getGender().equals("M"))
                    this.rbmale.toggle();
                else if(this.userEntity.getGender().equals("F"))
                    this.rbfemale.toggle();

                this.edciudad.setText(this.userEntity.getCity());
                this.edAddress.setText(this.userEntity.getAddress());

                if(this.userEntity.getCp() == "")
                    this.numcp.setText("");
                else
                    this.numcp.setText(this.userEntity.getCp().toString());

                this.numAge.setText(this.userEntity.getAge().toString());
                this.numHeight.setText(this.userEntity.getHeight());
                this.numWeight.setText(this.userEntity.getWeight().toString());



                break;
        }
    }


    /**
     * Método que gestiona los eventos de click
     * @author Domingo Lopez
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;

            case R.id.btnAddUser:

                Boolean validacion = validarCampos();

                if(validacion){

                    String name = this.edName.getText().toString();
                    String lastname = this.edLastName.getText().toString();
                    int age = Integer.parseInt(this.numAge.getText().toString());
                    String email = this.edEmail.getText().toString();

                    String phone = "";
                    if(!this.numPhone.getText().toString().isEmpty())
                        phone = this.numPhone.getText().toString().replace(" ","");

                    String phone2 = "";
                    if(!this.numPhone2.getText().toString().isEmpty())
                        phone2 = this.numPhone2.getText().toString().replace(" ","");

                    String cp = "";
                    if(!this.numcp.getText().toString().isEmpty())
                        cp =this.numcp.getText().toString().replace(" ","");

                    String direccion = this.edAddress.getText().toString();
                    String city = this.edciudad.getText().toString();
                    String height = this.numHeight.getText().toString();
                    int weight = Integer.parseInt(this.numWeight.getText().toString());

                    String gender =" ";
                    int genero = this.GenderGroup.getCheckedRadioButtonId();
                    if(genero == R.id.rbMujer)
                        gender = "F";
                    else if(genero == R.id.rbVaron)
                        gender = "M";
                    else
                        gender = " ";

                   if(this.type == UserActionDialog.NEW_USER){
                       String instant = Clock.systemUTC().instant().toString();
                       UserEntity user = new UserEntity(0,instant, name, lastname,email,gender, age, height, weight,phone, phone2, city,direccion,cp);
                       PatientListViewModel patientListViewModel = new ViewModelProvider(this.getActivity()).get(PatientListViewModel.class);
                       patientListViewModel.setNewPatient(user);
                       getDialog().dismiss();

                   }else if(this.type == UserActionDialog.MODIFY_USER){

                       UserEntity user = new UserEntity(this.userEntity.getUser_id(),this.userEntity.createdAt, name, lastname,email,gender, age, height, weight,phone, phone2, city,direccion,cp);
                       PatientDetailsViewModel patientDetailsViewModel = new ViewModelProvider(this.getActivity()).get(PatientDetailsViewModel.class);
                       patientDetailsViewModel.getPatientDetails(user);
                       patientDetailsViewModel.refreshPatientDetails();
                       getDialog().dismiss();
                   }

                }

                break;
        }

    }

    /**
     * Método que valida si los campos requeridos están rellenos y se procesan
     * @author Domingo Lopez
     * @return Boolean
     */
    private Boolean validarCampos() {

        //Diálogo de nuevo usuario

        Boolean valido = false;

        String username = this.edName.getText().toString();
        String lastname = this.edLastName.getText().toString();
        String email =  this.edEmail.getText().toString();
        int gender = this.GenderGroup.getCheckedRadioButtonId();
        String genero = "";

        if(gender == R.id.rbMujer)
            genero = "F";
        else if(gender == R.id.rbVaron)
            genero = "M";
        else
            genero = " ";

        String age = this.numAge.getText().toString();
        String height = this.numHeight.getText().toString();
        String weight = this.numWeight.getText().toString();
        String phone = this.numPhone.getText().toString();

        //Validación
        if(username.isEmpty())
            this.edName.setError("Nombre requerido");
        else if (lastname.isEmpty())
            this.edLastName.setError("Apellidos requeridos");
        else if(age.isEmpty())
            this.numAge.setError("Edad requerida");
        else if(height.isEmpty())
            this.numHeight.setError("Altura requerida");
        else if(weight.isEmpty())
            this.numWeight.setError("Peso requerido");
        else if(genero.isEmpty()) {
            this.rbfemale.setError("Género requerido");
            this.rbmale.setError("Género requerido");
        }else if(email.isEmpty())
            this.edEmail.setError("Email requerido");
        else if(phone.isEmpty())
            this.numPhone.setError("Teléfono requerido");
        else
            valido = true;


        return valido;

    }


}