package com.hdrescuer.hdrescuer.ui.ui.users;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.hdrescuer.hdrescuer.R;

public class NewUserDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText edName;
    EditText edLastName;
    EditText edEmail;
    ToggleButton tgSex;
    EditText numAge;
    EditText numHeight;
    EditText numWeight;
    EditText numPhone;
    Button add_user_btn;
    ImageView close_btn;

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

        return view;
    }

    private void findViews(View view) {

        this.edName = view.findViewById(R.id.txtName);
        this.edLastName = view.findViewById(R.id.txtlastName);
        this.edEmail = view.findViewById(R.id.txtEmail);
        this.tgSex = view.findViewById(R.id.tglSex);
        this.numAge = view.findViewById(R.id.numAge);
        this.numHeight = view.findViewById(R.id.numHeight);
        this.numWeight = view.findViewById(R.id.numWeight);
        this.numPhone = view.findViewById(R.id.numPhone);
        this.add_user_btn = view.findViewById(R.id.btnAddUser);
        this.close_btn = view.findViewById(R.id.btn_close);

        this.add_user_btn.setOnClickListener(this);
        this.close_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_close:
                getDialog().dismiss();
                break;

            case R.id.btnAddUser:

                break;
        }
    }



}