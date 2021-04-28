package com.hdrescuer.hdrescuer.ui.ui.charts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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


public class SessionResultActivity extends AppCompatActivity implements View.OnClickListener{




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
    ImageView backButton;

    //Algunos Atributos que puestos aquí facilitan la modularidad
    int steps_tic = 0; //Pasos del TicWatch


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_result);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();
        //Iniciamos la lista de gráficos
        this.list = new ArrayList<>();
        this.listView = findViewById(R.id.listView1);
        this.backButton = findViewById(R.id.btn_back_new_monitoring_session);

        //Obtenemos el intent recibido con el id_Session_local
        Intent intent = getIntent();
        int id_session_local = intent.getIntExtra("id_session_local",0);

        if(id_session_local != 0 && (Integer)id_session_local != null){
            this.sessionsRepository = new SessionsRepository(getApplication());
            SessionEntity session = this.sessionsRepository.getByIdSession(id_session_local);


            if(session.e4band) {
                this.e4BandRepository = new E4BandRepository(getApplication());
                ArrayList<LineData> lineDataArrayEmp = generateE4BandData(id_session_local);
                list.add(new EmpaticaChartItem(lineDataArrayEmp,getApplicationContext()));
            }

            if(session.ticwatch) {
                //Generamos los gráficos para el Reloj
                ArrayList<LineData> lineDataArray = generateTicWatchData(id_session_local);
                //Añadimos a la lista el array de lineData y los pasos
                list.add(new TicWatchChartItem(lineDataArray, this.steps_tic, getApplicationContext()));
            }

            if(session.ehealthboard) {
                this.eHealthBoardRepository = new EHealthBoardRepository(getApplication());
                //generateHealthBoardData()
            }


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


    /**
     * Generación de datos para la sesión almacenada para la pulsera empática
     * @param id_session_local
     * @return
     */
    ArrayList<LineData> generateE4BandData(int id_session_local){

        this.e4BandRepository = new E4BandRepository(getApplication());
        this.empaticaEntities = this.e4BandRepository.getByIdSession(id_session_local);

        /*Acelerómetro con gravedad*/
        LineData acc = generateAccData_Emp();
        LineData bvp = generateBVPData_Emp();
        LineData hr = generateHRData_Emp();

        ArrayList<LineData> arr = new ArrayList<>();
        arr.add(acc);
        arr.add(bvp);
        arr.add(hr);

        return arr;


    }


    LineData generateAccData_Emp(){
        //AccX
        ArrayList<Entry> datasetAccX = new ArrayList<>();
        for(int i = 0; i< empaticaEntities.size(); i++){
            datasetAccX.add(new Entry(i, empaticaEntities.get(i).e4_accx));
        }
        LineDataSet set1 = new LineDataSet(datasetAccX, "AccX");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);

        //AccY
        ArrayList<Entry> datasetAccY = new ArrayList<>();
        for(int i = 0; i< empaticaEntities.size(); i++){
            datasetAccY.add(new Entry(i, empaticaEntities.get(i).e4_accy));
        }

        LineDataSet set2 = new LineDataSet(datasetAccY, "AccY");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(4.5f);
        set2.setHighLightColor(Color.rgb(0, 0, 255));
        set2.setColor(Color.rgb(0, 0, 255));
        /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setCircleColor(Color.rgb(0, 0, 255));
        set2.setDrawValues(true);
        set2.setDrawCircleHole(false);

        //AccZ
        ArrayList<Entry> datasetAccZ = new ArrayList<>();
        for(int i = 0; i< empaticaEntities.size(); i++){
            datasetAccZ.add(new Entry(i, empaticaEntities.get(i).e4_accz));
        }

        LineDataSet set3 = new LineDataSet(datasetAccZ, "AccZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set3.setColor(Color.rgb(0, 255, 0));
        /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setCircleColor(Color.rgb(0, 255, 0));
        set3.setDrawValues(true);
        set3.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSetsAcc = new ArrayList<>();
        dataSetsAcc.add(set1);
        dataSetsAcc.add(set2);
        dataSetsAcc.add(set3);
        LineData data = new LineData(dataSetsAcc);

        return data;


    }

    LineData generateBVPData_Emp(){

        ArrayList<Entry> datasetBVP= new ArrayList<>();
        for(int i = 0; i< empaticaEntities.size(); i++){
            datasetBVP.add(new Entry(i, empaticaEntities.get(i).e4_bvp));
        }
        LineDataSet set1 = new LineDataSet(datasetBVP, "BVP Empatica E4Band");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);
        set1.setHighlightEnabled(true);


        ArrayList<ILineDataSet> dataSetsBVP = new ArrayList<>();
        dataSetsBVP.add(set1);
        LineData data = new LineData(dataSetsBVP);

        return data;

    }


    LineData generateHRData_Emp(){

        ArrayList<Entry> datasetHR= new ArrayList<>();
        for(int i = 0; i< empaticaEntities.size(); i++){
            datasetHR.add(new Entry(i, empaticaEntities.get(i).e4_hr));
        }
        LineDataSet set1 = new LineDataSet(datasetHR, "Heart Rate Empatica E4Band");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);
        set1.setHighlightEnabled(true);


        ArrayList<ILineDataSet> dataSetsHR = new ArrayList<>();
        dataSetsHR.add(set1);
        LineData data = new LineData(dataSetsHR);

        return data;

    }





    ArrayList<LineData> generateTicWatchData(int id_session_local){

        this.ticWatchRepository = new TicWatchRepository(getApplication());
        this.ticWatchEntities = this.ticWatchRepository.getByIdSession(id_session_local);

        /*Acelerómetro con gravedad*/
        LineData acc = generateAccData_Tic();
        LineData accl = generateAcclData_Tic();
        LineData gir = generateGirData_Tic();
        LineData hr = generateHRData_Tic();
        ArrayList<LineData> arr = new ArrayList<>();
        arr.add(acc);
        arr.add(accl);
        arr.add(gir);
        arr.add(hr);

        this.steps_tic = this.ticWatchRepository.getTicWatchMaxStepById(id_session_local);


        return arr;


    }



    private LineData generateAccData_Tic() {
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
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);

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
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setCircleColor(Color.rgb(0, 0, 255));
        set2.setDrawValues(true);
        set2.setDrawCircleHole(false);

        //AccZ
        ArrayList<Entry> datasetAccZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAccZ.add(new Entry(i, ticWatchEntities.get(i).tic_accz));
        }

        LineDataSet set3 = new LineDataSet(datasetAccZ, "AccZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set3.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setCircleColor(Color.rgb(0, 255, 0));
        set3.setDrawValues(true);
        set3.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSetsAcc = new ArrayList<>();
        dataSetsAcc.add(set1);
        dataSetsAcc.add(set2);
        dataSetsAcc.add(set3);
        LineData data = new LineData(dataSetsAcc);

       return data;

    }

    private LineData generateAcclData_Tic() {

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
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);

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
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setCircleColor(Color.rgb(0, 0, 255));
        set2.setDrawValues(true);
        set2.setDrawCircleHole(false);

        //AccZ
        ArrayList<Entry> datasetAcclZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetAcclZ.add(new Entry(i, ticWatchEntities.get(i).tic_acclz));
        }

        LineDataSet set3 = new LineDataSet(datasetAcclZ, "AcclZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set3.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setCircleColor(Color.rgb(0, 255, 0));
        set3.setDrawValues(true);
        set3.setDrawCircleHole(false);


        ArrayList<ILineDataSet> dataSetsAccl = new ArrayList<>();
        dataSetsAccl.add(set1);
        dataSetsAccl.add(set2);
        dataSetsAccl.add(set3);
        LineData data = new LineData(dataSetsAccl);

        return data;


    }

    private LineData generateGirData_Tic() {

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
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);

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
            /*set2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set2.setCircleColor(Color.rgb(0, 0, 255));
        set2.setDrawValues(true);
        set2.setDrawCircleHole(false);

        //GirZ
        ArrayList<Entry> datasetGirZ = new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetGirZ.add(new Entry(i, ticWatchEntities.get(i).tic_girz));
        }

        LineDataSet set3 = new LineDataSet(datasetGirZ, "GirZ");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(4.5f);
        set3.setHighLightColor(Color.rgb(0, 255, 0));
        set3.setColor(Color.rgb(0, 255, 0));
           /* set3.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);*/
        set3.setCircleColor(Color.rgb(0, 255, 0));
        set3.setDrawValues(true);
        set3.setDrawCircleHole(false);


        ArrayList<ILineDataSet> dataSetsGir = new ArrayList<>();
        dataSetsGir.add(set1);
        dataSetsGir.add(set2);
        dataSetsGir.add(set3);
        LineData data = new LineData(dataSetsGir);

        return data;

    }

    private LineData generateHRData_Tic() {

        ArrayList<Entry> datasetHr= new ArrayList<>();
        for(int i = 0; i< ticWatchEntities.size(); i++){
            datasetHr.add(new Entry(i,  Math.round(ticWatchEntities.get(i).tic_hrppg)));
        }
        LineDataSet set1 = new LineDataSet(datasetHr, "Heart Rate Tic Watch");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4.5f);
        set1.setColor(Color.rgb(255, 0, 0));
        set1.setHighLightColor(Color.rgb(255, 0, 0));
        set1.setCircleColor(Color.rgb(255, 0, 0));
        set1.setDrawValues(true);
        set1.setDrawCircleHole(false);
        set1.setHighlightEnabled(true);


        ArrayList<ILineDataSet> dataSetsGir = new ArrayList<>();
        dataSetsGir.add(set1);
        LineData data = new LineData(dataSetsGir);

        return data;

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_new_monitoring_session:
                finish();
                break;
        }
    }




}