package com.hdrescuer.hdrescuer.ui.ui.localsessions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.retrofit.request.Session;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.ui.ui.users.ListItemClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
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


            holder.tvTimestampini.setText(dateFormat.format(Date.from(Instant.parse(holder.mItem.getTimestamp_ini()))));
            holder.id_session_local.setText(Integer.toString(holder.mItem.getId_session_local()));
            holder.duration.setText(Constants.getHMS(holder.mItem.getTotal_time()));

            if(holder.mItem.getDescription().equals(""))
                holder.description.setText("No hay descripción para esta sesión");
            else
                holder.description.setText(holder.mItem.getDescription());


            String values[]= {"Orange","Apple","Pineapple"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyApp.getContext(),R.layout.simple_list_item_1,values);
            holder.autoCompleteTextView.setAdapter(adapter);



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

        public final TextView tvTimestampini;
        public final TextView id_session_local;
        public final TextView duration;
        public final TextView description;
        public final AutoCompleteTextView autoCompleteTextView;
        public final Button btnUploadSession;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTimestampini = view.findViewById(R.id.tvTimestampIni);
            id_session_local = view.findViewById(R.id.tvSessionLocal);
            duration = view.findViewById(R.id.tvDuration);
            description = view.findViewById(R.id.tvDescription);
            autoCompleteTextView = view.findViewById(R.id.tvAutoCompletablePatient);
            btnUploadSession = view.findViewById(R.id.btnUploadSessionLocale);


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