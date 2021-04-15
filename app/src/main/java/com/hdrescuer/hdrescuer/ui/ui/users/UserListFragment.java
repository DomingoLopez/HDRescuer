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
 * A fragment representing a list of Items.
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


        this.userListViewModel = new ViewModelProvider(getActivity()).get(UserListViewModel.class);
        alreadyCreated = true;

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
        this.recyclerView = view.findViewById(R.id.list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

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


    @Override
    public void onResume() {
        super.onResume();
        if(!alreadyCreated){
            refreshUserDetails();
        }
        alreadyCreated = false;
    }

    private void refreshUserDetails() {
        this.userListViewModel.refreshUsers();
    }


    private void findViews(View view) {
        this.btn = view.findViewById(R.id.user_add_btn);
        this.btn.setOnClickListener(this);
    }


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

    @Override
    public void onListItemClick(int position) {

        String id = this.userList.get(position).getId();
        Intent i = new Intent(MyApp.getContext(), UserDetailsActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

        NewUserDialogFragment dialog = new NewUserDialogFragment(UserActionDialog.NEW_USER,null);
        dialog.show(this.getActivity().getSupportFragmentManager(), "NewUserFragment");


    }


}