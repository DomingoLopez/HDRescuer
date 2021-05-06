package com.hdrescuer.hdrescuer.ui.ui.localsessions;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.Log;
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

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase RecyclerView para la lista de usuarios
 * @author Domingo Lopez
 */
public class MySessionsRecyclerViewAdapter extends RecyclerView.Adapter<MySessionsRecyclerViewAdapter.ViewHolder> {

    private List<SessionEntity> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;
    public List<User> users;
    public List<String> usuarios_a_predecir = new ArrayList<>();


    public MySessionsRecyclerViewAdapter(Context contexto, List<SessionEntity> items, List<User> users, ListItemClickListener onClickListener) {
        this.ctx = contexto;
        this.mValues = items;
        this.mOnClickListener = onClickListener;
        this.users = users;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        seteaUsuariosPredictivo();


    }

    void seteaUsuariosPredictivo(){
        if(this.users != null){
            for(int i = 0; i< this.users.size(); i++){
                this.usuarios_a_predecir.add(this.users.get(i).getLastname()+", "+this.users.get(i).getUsername());
            }
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_session, parent, false);
        return new ViewHolder(view, mOnClickListener);
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

            //Falta obtener el nombre e ids de los usuarios. Los puedo obtener del viewmodel de los usuarios que se carga en el HOME
            //Después gestionar el click en cada item para subir y echar a un csv la sesión entera y mandarla al servidor
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyApp.getContext(),R.layout.simple_list_item_1,this.usuarios_a_predecir);
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
        private WeakReference<ListItemClickListener> listenerRef;

        public ViewHolder(View view, ListItemClickListener listener) {
            super(view);

            listenerRef = new WeakReference<>(listener);

            mView = view;
            tvTimestampini = view.findViewById(R.id.tvTimestampIni);
            id_session_local = view.findViewById(R.id.tvSessionLocal);
            duration = view.findViewById(R.id.tvDuration);
            description = view.findViewById(R.id.tvDescription);
            autoCompleteTextView = view.findViewById(R.id.tvAutoCompletablePatient);
            btnUploadSession = view.findViewById(R.id.btnUploadSessionLocale);

            //Seteamos el listener para cada botón del holder
            btnUploadSession.setOnClickListener(this);
            view.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == btnUploadSession.getId()){

                String user_elegido = autoCompleteTextView.getText().toString();
                autoCompleteTextView.clearFocus();
                autoCompleteTextView.setText("");


                listenerRef.get().onListItemClickUser(getAdapterPosition(), user_elegido);

            }

        }
    }
}