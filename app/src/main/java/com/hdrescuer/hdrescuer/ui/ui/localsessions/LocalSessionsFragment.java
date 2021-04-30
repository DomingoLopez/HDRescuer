package com.hdrescuer.hdrescuer.ui.ui.localsessions;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.SessionsListViewModel;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.ui.ui.users.ListItemClickListener;

import java.util.List;


public class LocalSessionsFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    MySessionsRecyclerViewAdapter adapter;
    List<SessionEntity> sessionList;
    SessionsListViewModel sessionsListViewModel;
    FloatingActionButton button;

    boolean alreadyCreated = false;


    public LocalSessionsFragment() {
    }


    @SuppressWarnings("unused")
    public static LocalSessionsFragment newInstance(int columnCount) {
        LocalSessionsFragment fragment = new LocalSessionsFragment();
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


        this.sessionsListViewModel = new ViewModelProvider(requireActivity()).get(SessionsListViewModel.class);
        alreadyCreated = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sessions_list, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list_sessions);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.adapter = new MySessionsRecyclerViewAdapter(
                getActivity(),
                this.sessionList,
                this
        );
        this.recyclerView.setAdapter(adapter);


        findViews(view);
        loadSessionsData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!alreadyCreated){
/*
            refreshSessions();
*/
        }
        alreadyCreated = false;
    }


/*
    private void refreshSessions() {
        this.sessionsListViewModel.refreshSessions();
    }
*/


    /**
     * Método que busca los elementos de la vista
     * @author Domingo Lopez
     * @param view
     */
    private void findViews(View view) {
        this.button = view.findViewById(R.id.btn_delete_sessions);
        this.button.setOnClickListener(this);
    }


    /**
     * Método que añade observer al viewmodel para observar cambios en la lista de usuarios
     * @author Domingo Lopez
     */
    private void loadSessionsData() {

        this.sessionsListViewModel.getSessions().observe(getActivity(), new Observer<List<SessionEntity>>() {
            @Override
            public void onChanged(List<SessionEntity> sessions) {

                sessionList = sessions;
                adapter.setData(sessionList);
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

        int id = this.sessionList.get(position).id_session_local;
       /* Intent i = new Intent(MyApp.getContext(), UserDetailsActivity.class);
        i.putExtra("id", id);
        startActivity(i);*/
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_delete_sessions:
                this.sessionsListViewModel.deleteSessions();
                break;
        }


    }


}