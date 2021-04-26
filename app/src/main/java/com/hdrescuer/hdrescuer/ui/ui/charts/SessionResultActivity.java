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

import com.github.mikephil.charting.data.ChartData;
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
import com.hdrescuer.hdrescuer.db.entity.EmpaticaEntity;
import com.hdrescuer.hdrescuer.db.entity.HealthBoardEntity;
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

    //Lista de entidades que vamos a obtener
    List<TicWatchEntity> ticWatchEntities = null;
    List<EmpaticaEntity> empaticaEntities = null;
    List<HealthBoardEntity> healthBoardEntities = null;

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



            if(session.e4band) {
                this.e4BandRepository = new E4BandRepository(getApplication());
                //generateE4BandData();
            }

            if(session.ticwatch) {
                ArrayList<LineData> lineDataArray = generateTicWatchData(id_session_local);
                //Añadimos a la lista el Acc con gravedad
                list.add(new TicWatchChartItem(lineDataArray, getApplicationContext()));

                ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                listView.setAdapter(cda);
            }

            if(session.ehealthboard) {
                this.eHealthBoardRepository = new EHealthBoardRepository(getApplication());
                //generateHealthBoardData()
            }







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




    ArrayList<LineData> generateTicWatchData(int id_session_local){

        this.ticWatchRepository = new TicWatchRepository(getApplication());
        this.ticWatchEntities = this.ticWatchRepository.getByIdSession(id_session_local);

        /*Acelerómetro con gravedad*/
        LineData acc = generateAccData();
        LineData accl = generateAcclData();
        LineData gir = generateGirData();
        //LineData hr = generateHRData();
        ArrayList<LineData> arr = new ArrayList<>();
        arr.add(acc);
        arr.add(accl);
        arr.add(gir);


        return arr;


    }




    private LineData generateAccData() {
        //AccX
        ArrayList<Entry> datasetAccX = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAccX.add(new Entry(i, ticWatchEntities.get(i).tic_accx));
        }
        LineDataSet set1 = new LineDataSet(datasetAccX, "AccX");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);

        //AccY
        ArrayList<Entry> datasetAccY = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAccY.add(new Entry(i, ticWatchEntities.get(i).tic_accy));
        }

        LineDataSet set2 = new LineDataSet(datasetAccY, "AccY");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(4.5f);
        set2.setHighLightColor(Color.rgb(0, 0, 255));
        set2.setColor(Color.rgb(0, 0, 255));
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setDrawValues(true);

        //AccZ
        ArrayList<Entry> datasetAccZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAccZ.add(new Entry(i, ticWatchEntities.get(i).tic_accz));
        }

        LineDataSet set3 = new LineDataSet(datasetAccZ, "AccZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set2.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setDrawValues(true);

        ArrayList<ILineDataSet> dataSetsAcc = new ArrayList<>();
        dataSetsAcc.add(set1);
        dataSetsAcc.add(set2);
        dataSetsAcc.add(set3);
        LineData data = new LineData(dataSetsAcc);

       return data;

    }

    private LineData generateAcclData() {

        /*Acelerómetro linear*/
        //AcclX
        ArrayList<Entry> datasetAcclX = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAcclX.add(new Entry(i, ticWatchEntities.get(i).tic_acclx));
        }
        LineDataSet set1 = new LineDataSet(datasetAcclX, "AcclX");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);

        //AcclY
        ArrayList<Entry> datasetAccYl = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAccYl.add(new Entry(i, ticWatchEntities.get(i).tic_accly));
        }

        LineDataSet set2 = new LineDataSet(datasetAccYl, "AcclY");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(4.5f);
        set2.setHighLightColor(Color.rgb(0, 0, 255));
        set2.setColor(Color.rgb(0, 0, 255));
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setDrawValues(true);

        //AccZ
        ArrayList<Entry> datasetAcclZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAcclZ.add(new Entry(i, ticWatchEntities.get(i).tic_acclz));
        }

        LineDataSet set3 = new LineDataSet(datasetAcclZ, "AcclZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set2.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setDrawValues(true);


        ArrayList<ILineDataSet> dataSetsAccl = new ArrayList<>();
        dataSetsAccl.add(set1);
        dataSetsAccl.add(set2);
        dataSetsAccl.add(set3);
        LineData data = new LineData(dataSetsAccl);

        return data;


    }

    private LineData generateGirData() {

        /*Giroscopio */
        //GirX
        ArrayList<Entry> datasetGirX = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetGirX.add(new Entry(i, ticWatchEntities.get(i).tic_girx));
        }
        LineDataSet set1 = new LineDataSet(datasetGirX, "GirX");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);

        //GirY
        ArrayList<Entry> datasetGirY = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetGirY.add(new Entry(i, ticWatchEntities.get(i).tic_giry));
        }

        LineDataSet set2 = new LineDataSet(datasetGirY, "GirY");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(4.5f);
        set2.setHighLightColor(Color.rgb(0, 0, 255));
        set2.setColor(Color.rgb(0, 0, 255));
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setDrawValues(true);

        //GirZ
        ArrayList<Entry> datasetGirZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetGirZ.add(new Entry(i, ticWatchEntities.get(i).tic_girz));
        }

        LineDataSet set3 = new LineDataSet(datasetGirZ, "AcclZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set2.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            set3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setDrawValues(true);


        ArrayList<ILineDataSet> dataSetsGir = new ArrayList<>();
        dataSetsGir.add(set1);
        dataSetsGir.add(set2);
        dataSetsGir.add(set3);
        LineData data = new LineData(dataSetsGir);

        return data;

    }


    void generateResults(ArrayList<LineData> lineDataArray){


    }




}