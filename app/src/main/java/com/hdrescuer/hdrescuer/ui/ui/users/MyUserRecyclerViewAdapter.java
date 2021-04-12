package com.hdrescuer.hdrescuer.ui.ui.users;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.retrofit.response.User;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private List<User> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;

    public MyUserRecyclerViewAdapter(Context contexto,List<User> items, ListItemClickListener onClickListener) {
        this.ctx = contexto;
        this.mValues = items;
        this.mOnClickListener = onClickListener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);

            holder.user_name.setText(holder.mItem.getUsername() + " " +holder.mItem.getLastname());

            if(holder.mItem.getTimestamp_ini() != null)
                holder.last_monitoring.setText(dateFormat.format(holder.mItem.getTimestamp_ini()));
            else
                holder.last_monitoring.setText("- -");

            if(holder.mItem.getTotal_time() != null)
                holder.sesion_duration.setText(holder.mItem.getTotal_time().toString());
            else
                holder.sesion_duration.setText("- -");
        }
    }

    public void setData(List<User> users){
        this.mValues = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if(this.mValues != null)
            return mValues.size();
        else
            return 0;

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView user_name;
        public final TextView last_monitoring;
        public final TextView sesion_duration;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            user_name = (TextView) view.findViewById(R.id.textViewUserName);
            last_monitoring = (TextView) view.findViewById(R.id.tvLastSession);
            sesion_duration = (TextView) view.findViewById(R.id.tvTotalTime);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + user_name.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
}