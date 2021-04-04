package com.hdrescuer.hdrescuer.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.UserDetailsViewModel;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClientUsersModule;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

public class NewUserDialogFragment extends DialogFragment implements View.OnClickListener {

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
    EditText edpassword;
    ImageButton add_user_btn;
    ImageButton close_btn;
    EditText numPhone2;
    EditText edAddress;
    EditText edciudad;
    EditText numcp;

    UserActionDialog type;
    UserDetails userDetails;

    //Servicio de api
    AuthConectionClientUsersModule authConectionClientUsersModule;
    AuthApiService authApiService;


    public NewUserDialogFragment(UserActionDialog type, UserDetails userDetails){
        this.type = type;
        this.userDetails = userDetails;
        this.authConectionClientUsersModule = AuthConectionClientUsersModule.getInstance();
        this.authApiService = this.authConectionClientUsersModule.getAuthApiService();
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

    private void findViews(View view) {

        this.edName = view.findViewById(R.id.txtName);
        this.edLastName = view.findViewById(R.id.txtlastName);
        this.edEmail = view.findViewById(R.id.txtEmail);
        this.edpassword = view.findViewById(R.id.txtPassword);
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
                this.edciudad.setText("");
                this.edpassword.setText("");
                this.numcp.setText("");

                break;

            case MODIFY_USER:
                //Diálogo para modificar el usuario
                this.edName.setText(this.userDetails.getUsername());
                this.edLastName.setText(this.userDetails.getLastname());
                this.edEmail.setText(this.userDetails.getEmail());
                this.edpassword.setHint("Nueva Contraseña");

                if(this.userDetails.getPhone2() == 0)
                    this.numPhone2.setText("");
                else
                    this.numPhone2.setText(this.userDetails.getPhone2().toString());

                if(this.userDetails.getPhone() == 0)
                    this.numPhone.setText("");
                else
                    this.numPhone.setText(this.userDetails.getPhone().toString());

                if(this.userDetails.getGender().equals("M"))
                    this.rbmale.toggle();
                else if(this.userDetails.getGender().equals("F"))
                    this.rbfemale.toggle();

                this.edciudad.setText(this.userDetails.getCity());
                this.edAddress.setText(this.userDetails.getAddress());

                if(this.userDetails.getCp() == 0)
                    this.numcp.setText("");
                else
                    this.numcp.setText(this.userDetails.getCp().toString());

                this.numAge.setText(this.userDetails.getAge().toString());
                this.numHeight.setText(this.userDetails.getHeight());
                this.numWeight.setText(this.userDetails.getWeight().toString());



                break;
        }
    }



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

                    int phone = 0;
                    if(!this.numPhone.getText().toString().isEmpty())
                        phone = Integer.parseInt(this.numPhone.getText().toString().replace(" ",""));

                    int phone2 = 0;
                    if(!this.numPhone2.getText().toString().isEmpty())
                        phone2 = Integer.parseInt(this.numPhone2.getText().toString().replace(" ",""));

                    int cp = 0;
                    if(!this.numcp.getText().toString().isEmpty())
                        cp = Integer.parseInt(this.numcp.getText().toString().replace(" ",""));

                    String direccion = this.edAddress.getText().toString();
                    String city = this.edciudad.getText().toString();
                    String password = this.edpassword.getText().toString();
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

                       UserDetails user = new UserDetails(null, name, lastname,email,password,gender, age, height, weight,phone, phone2, city,direccion,cp);
                       UserListViewModel userListViewModel = new ViewModelProvider(this.getActivity()).get(UserListViewModel.class);
                       userListViewModel.setNewUser(user);
                       getDialog().dismiss();

                   }else if(this.type == UserActionDialog.MODIFY_USER){

                       UserDetails user = new UserDetails(this.userDetails.getId(), name, lastname,email,password,gender, age, height, weight,phone, phone2, city,direccion,cp);
                       UserDetailsViewModel userDetailsViewModel = new ViewModelProvider(this.getActivity()).get(UserDetailsViewModel.class);
                       userDetailsViewModel.updateUserDetails(user);
                       getDialog().dismiss();
                   }

                }

                break;
        }

    }

    private Boolean validarCampos() {

        //Diálogo de nuevo usuario

        Boolean valido = false;

        String username = this.edName.getText().toString();
        String lastname = this.edLastName.getText().toString();
        String email =  this.edEmail.getText().toString();
        String password = this.edpassword.getText().toString();
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
        else if(password.isEmpty()){
            if(this.type == UserActionDialog.NEW_USER)
                this.edpassword.setError("Contraseña requerida para nuevo usuario");
            else
                valido = true;
        }
        else
            valido = true;


        return valido;

    }


}