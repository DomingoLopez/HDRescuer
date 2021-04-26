package com.hdrescuer.hdrescuer.ui.ui.charts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.dbrepositories.E4BandRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.EHealthBoardRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.SessionsRepository;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.SessionEntity;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.util.ArrayList;
import java.util.List;


public class SessionResultActivity extends AppCompatActivity {




    //Repositorios que contienen la conexión con los DAO. Llegados a este punto podemos crear unos nuevos o bien separar los dao o usar un repositorio Common para las consultas

    private E4BandRepository e4BandRepository;
    private EHealthBoardRepository eHealthBoardRepository;
    private TicWatchRepository ticWatchRepository;
    private SessionsRepository sessionsRepository;

    //Lista de gráficos que va a tener la pantalla
    ArrayList<ChartItem> list;

    //Lista en la vista
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_result);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();
        //Iniciamos la lista de gráficos
        this.list = new ArrayList<>();
        this.listView = findViewById(R.id.listView1);

        //Obtenemos el intent recibido con el id_Session_local
        Intent intent = getIntent();
        //int id_session_local = intent.getIntExtra("id_session_local",0);
        //Pruebas
        int id_session_local = 12;

        if(id_session_local != 0 && (Integer)id_session_local != null){
            this.sessionsRepository = new SessionsRepository(getApplication());
            SessionEntity session = this.sessionsRepository.getByIdSession(id_session_local);

            this.ticWatchRepository = new TicWatchRepository(getApplication());
            List<TicWatchEntity> ticWatchEntities = this.ticWatchRepository.getByIdSession(id_session_local);

           /* if(session.e4band) {
                this.e4BandRepository = new E4BandRepository(getApplication());
                //generateE4BandData();
            }

            if(session.ticwatch) {
                generateTicWatchData(id_session_local);
            }

            if(session.ehealthboard) {
                this.eHealthBoardRepository = new EHealthBoardRepository(getApplication());
                //generateHealthBoardData()
            }



            generateResults();*/

            ArrayList<Entry> dataset = new ArrayList<>();
            for(int i = 0; i< ticWatchEntities.size(); i++){
                dataset.add(new Entry(i, ticWatchEntities.get(i).tic_accx));
            }

            LineDataSet set1 = new LineDataSet(dataset, "AccX");
            set1.setLineWidth(2.5f);
            set1.setCircleRadius(4.5f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawValues(false);

            ArrayList<Entry> dataset1 = new ArrayList<>();
            for(int i = 0; i< ticWatchEntities.size(); i++){
                dataset1.add(new Entry(i, ticWatchEntities.get(i).tic_accy));
            }

            LineDataSet set2 = new LineDataSet(dataset1, "AccY");
            set2.setLineWidth(2.5f);
            set2.setCircleRadius(4.5f);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set2.setDrawValues(false);

            ArrayList<Entry> dataset2 = new ArrayList<>();
            for(int i = 0; i< ticWatchEntities.size(); i++){
                dataset2.add(new Entry(i, ticWatchEntities.get(i).tic_hrppg));
            }

            LineDataSet set3 = new LineDataSet(dataset2, "Heart Rate Tic Watch");
            set3.setLineWidth(2.5f);
            set3.setCircleRadius(4.5f);
            set3.setHighLightColor(Color.rgb(244, 117, 117));
            set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set3.setDrawValues(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);
            //dataSets.add(set3);
            LineData data = new LineData(dataSets);



            list.add(new TicWatchChartItem(data, getApplicationContext()));
            list.add(new TicWatchChartItem(data, getApplicationContext()));
            list.add(new TicWatchChartItem(data, getApplicationContext()));
            list.add(new TicWatchChartItem(data, getApplicationContext()));

            ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
            listView.setAdapter(cda);

        }


    }


    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            //noinspection ConstantConditions
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            ChartItem ci = getItem(position);
            return ci != null ? ci.getItemType() : 0;
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }






    void generateTicWatchData(int id_session_local){



    }


    void generateResults(){

    }




}