package com.hdrescuer.hdrescuer.ui.ui.localsessions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.request.Session;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.ui.ui.users.ListItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Clase RecyclerView para la lista de usuarios
 * @author Domingo Lopez
 */
public class MySessionsRecyclerViewAdapter extends RecyclerView.Adapter<MySessionsRecyclerViewAdapter.ViewHolder> {

    private List<SessionEntity> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;

    public MySessionsRecyclerViewAdapter(Context contexto, List<SessionEntity> items, ListItemClickListener onClickListener) {
        this.ctx = contexto;
        this.mValues = items;
        this.mOnClickListener = onClickListener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_session, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);

            holder.user_name.setText("Aquí estamos");


        }
    }


    public void setData(List<SessionEntity> sessions){
        this.mValues = sessions;
        notifyDataSetChanged();
    }

    /**
     * Devuelve el número de elementos en la lista de usuarios
     * @author Domingo Lopez
     * @return int
     */
    @Override
    public int getItemCount() {

        if(this.mValues != null)
            return mValues.size();
        else
            return 0;

    }


    /**
     * Clase internta para añadir el ViewHolder a cada item del RecyclerView y gestionar eventos de click en cada usuario
     *  @author Domingo Lopez
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public SessionEntity mItem;

        public final TextView user_name;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            user_name = (TextView) view.findViewById(R.id.tvPrueba);


            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
}