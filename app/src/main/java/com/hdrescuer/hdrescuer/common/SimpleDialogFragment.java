package com.hdrescuer.hdrescuer.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.hdrescuer.hdrescuer.R;


public class SimpleDialogFragment extends DialogFragment {

    private OnSimpleDialogClick onSimpleDialogClick = null;
    private String action = null;
    EditText session_description;


    public SimpleDialogFragment(OnSimpleDialogClick ref, String action){
        onSimpleDialogClick = ref;
        this.action = action;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //Lanzamos diálogo para establecer la descripción de la sesión de cara a
        //recordar para qué era la sesión
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_simple, null);
        this.session_description = view.findViewById(R.id.ed_session_description);

        if(this.action.equals("SET_DESCRIPTION")){
            builder.setTitle("Añade una descripción a la sesión");

        }else if(this.action.equals("CONFIRM_SAVE")){
            builder.setTitle("¿Desea guardar la sesión?");
            this.session_description.setVisibility(View.INVISIBLE);
        }


        builder.setView(view);


        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    if(action.equals("SET_DESCRIPTION")){
                        String description = session_description.getText().toString();
                        onSimpleDialogClick.onPositiveButtonClick(description);
                    }else if(action.equals("CONFIRM_SAVE")){
                        onSimpleDialogClick.onPositiveButtonClick();
                    }
            }
        });
        builder.setNegativeButton(R.string.dialog_dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    if(action.equals("SET_DESCRIPTION")){
                        SimpleDialogFragment.this.getDialog().cancel();
                    }else if(action.equals("CONFIRM_SAVE")){
                        onSimpleDialogClick.onNegativeButtonClick();
                    }

            }
        });

        return builder.create();
    }

}
