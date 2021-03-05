package com.hdrescuer.hdrescuer.ui.ui.users;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.retrofit.response.User;


import java.util.List;


public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private final List<User> mValues;
    private Context ctx;

    public MyUserRecyclerViewAdapter(Context contexto,List<User> items) {
        this.ctx = contexto;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.user_name.setText(holder.mItem.getUsername());
        holder.last_monitoring.setText(holder.mItem.getLastMonitoring());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView user_name;
        public final TextView last_monitoring;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            user_name = (TextView) view.findViewById(R.id.textViewUserName);
            last_monitoring = (TextView) view.findViewById(R.id.textViewLastMonitoring);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + user_name.getText() + "'";
        }
    }
}