package com.hdrescuer.hdrescuer.ui.ui.users;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.retrofit.AuthApiService;
import com.hdrescuer.hdrescuer.retrofit.AuthConectionClient;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.ui.ui.support.SupportViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A fragment representing a list of Items.
 */
public class UserListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    MyUserRecyclerViewAdapter adapter;
    List<User> userList;
    AuthApiService authApiService;
    AuthConectionClient authConectionClient;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserListFragment newInstance(int columnCount) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



    /*


        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            this.recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            retrofitInit();
            loadUserData();
            recyclerView.setAdapter(adapter);
        }
        return view;*/

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        retrofitInit();
        loadUserData();
        recyclerView.setAdapter(adapter);

        return view;


    }

    private void retrofitInit() {
        this.authConectionClient = AuthConectionClient.getInstance();
        this.authApiService = this.authConectionClient.getAuthApiService();

    }

    private void loadUserData() {

        //Obtenemos usuarios
        Log.i("info","ENTROOOOOOOOOOOOOOOOOOOOO");
        Call<List<User>> call = authApiService.getAllUsers();
        /*call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    userList = response.body();
                    adapter = new MyUserRecyclerViewAdapter(
                            getActivity(),
                            userList
                    );
                }else {

                    User user = new User(1,"domin","2020-13-32");
                    userList.add(user);
                    adapter = new MyUserRecyclerViewAdapter(
                            getActivity(),
                            userList
                    );
                    Toast.makeText(getActivity(), "Error al cargar la lista de usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });*/

        User user = new User(1,"domin","2020-13-32");
        userList= new ArrayList<>();
        userList.add(user);
        adapter = new MyUserRecyclerViewAdapter(
                getActivity(),
                userList
        );
        Log.i("info","SALGOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");





    }
}