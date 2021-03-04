package com.hdrescuer.hdrescuer.ui.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.MyApp;

public class UsersFragment extends Fragment implements View.OnClickListener {

    private UsersViewModel usersViewModel;

    private FloatingActionButton add_user_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usersViewModel =
                new ViewModelProvider(this).get(UsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        final TextView textView = root.findViewById(R.id.text_users);
        usersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //findViews();
        //events();
        FloatingActionButton add_user_btn = root.findViewById(R.id.fab);
        add_user_btn.setOnClickListener(this);

        return root;

    }

    private void findViews() {

;
    }


    private void events() {

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MyApp.getContext(), "Botón tocado", Toast.LENGTH_SHORT).show();
    }







}