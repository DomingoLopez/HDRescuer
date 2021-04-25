package com.hdrescuer.hdrescuer.ui.ui.charts;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.navigation.NavigationView;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.UserListViewModel;
import com.hdrescuer.hdrescuer.data.dbrepositories.TicWatchRepository;
import com.hdrescuer.hdrescuer.db.entity.TicWatchEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SessionResultActivity extends AppCompatActivity {


    private LineChart exampleLineChart;
    private TicWatchRepository ticWatchRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_result);


        //Ocultamos el Toolbar del MainActivity para el login
        getSupportActionBar().hide();

        this.ticWatchRepository = new TicWatchRepository(this.getApplication());

        exampleLineChart = findViewById(R.id.example_line_chart);
        exampleLineChart.setTouchEnabled(true);
        exampleLineChart.setPinchZoom(true);



        //Obtenemos el id de la sesión a mostrar
        Intent intent = getIntent();
        int id_session_local = intent.getIntExtra("id_session_local",0);

        List<TicWatchEntity> ticwatch_session = this.ticWatchRepository.getByIdSession(id_session_local);
        Log.i("SESSION",ticwatch_session.toString());
        ArrayList<Entry> dataset = new ArrayList<>();
        for(int i = 0; i< ticwatch_session.size(); i++){
            dataset.add(new Entry(i, ticwatch_session.get(i).tic_hrppg));
        }

        LineDataSet set1 = new LineDataSet(dataset, "Sample data");
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        exampleLineChart.setData(data);

    }




}