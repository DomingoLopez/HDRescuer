package com.hdrescuer.hdrescuer.ui.ui.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.common.NewUserDialogFragment;
import com.hdrescuer.hdrescuer.common.UserActionDialog;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.ui.ui.userdetails.UserDetailsActivity;

import java.util.List;


/**
 * Fragmento que contiene la lista de usuarios cargados desde el servidor. Hereda de Fragment e implementa ListItemClickListener, una interfaz para detectar clicks en los items del RecyclerView
 *  @author Domingo Lopez
 */
public class UserListFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    MyUserRecyclerViewAdapter adapter;
    List<User> userList;
    UserListViewModel userListViewModel;
    FloatingActionButton btn;

    boolean alreadyCreated = false;

    /**
     * Constructor Vacío del fragmento
     * @author Domingo Lopez
     */
    public UserListFragment() {
    }


    @SuppressWarnings("unused")
    public static UserListFragment newInstance(int columnCount) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creación del fragmento de lista de usuarios
     * @author Domingo Lopez
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        this.userListViewModel = new ViewModelProvider(getActivity()).get(UserListViewModel.class);
        alreadyCreated = true;

    }

    /**
     * Inicializa la vista del Fragmento y carga los elementos de la misma, así como la información de los usuarios. Setea el adapter de la lista de usuarios
     * @author Domingo Lopez
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.adapter = new MyUserRecyclerViewAdapter(
                getActivity(),
                this.userList,
                this
        );
        this.recyclerView.setAdapter(adapter);


        findViews(view);
        loadUserData();

        return view;
    }

    /**
     * OnResume de lista de usuarios. Refresca la lista de usuarios por se ha habido cambios en los mismos.
     * @author Domingo Lopez
     */
    @Override
    public void onResume() {
        super.onResume();
        if(!alreadyCreated){
            refreshUserDetails();
        }
        alreadyCreated = false;
    }

    /**
     * Método que llama al viewmodel para refrescar usuarios
     * @author Domingo Lopez
     */
    private void refreshUserDetails() {
        this.userListViewModel.refreshUsers();
    }


    /**
     * Método que busca los elementos de la vista
     * @author Domingo Lopez
     * @param view
     */
    private void findViews(View view) {
        this.btn = view.findViewById(R.id.user_add_btn);
        this.btn.setOnClickListener(this);
    }


    /**
     * Método que añade observer al viewmodel para observar cambios en la lista de usuarios
     * @author Domingo Lopez
     */
    private void loadUserData() {

        this.userListViewModel.getUsers().observe(requireActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i("USERS", users.toString());
                userList = users;
                adapter.setData(userList);
            }
        });

    }

    /**
     * Método llamado al clickar en un item de la lista. Es un método de la clase implementada ListItemClickListener
     * @author Domingo Lopez
     * @param position
     */
    @Override
    public void onListItemClick(int position) {

        String id = this.userList.get(position).getId();
        Intent i = new Intent(MyApp.getContext(), UserDetailsActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    /**
     * Método para gestionar los eventos de click en los elementos del fragmento
     * @author Domingo Lopez
     * @param view
     */
    @Override
    public void onClick(View view) {

        NewUserDialogFragment dialog = new NewUserDialogFragment(UserActionDialog.NEW_USER,null);
        dialog.show(this.getActivity().getSupportFragmentManager(), "NewUserFragment");


    }


}