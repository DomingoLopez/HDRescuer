package com.hdrescuer.hdrescuer.ui.ui.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.hdrescuer.hdrescuer.R;

import java.util.ArrayList;


public class EmpaticaChartItem extends ChartItem {


    public EmpaticaChartItem(ArrayList<LineData> cd, Context c) {
        super(cd);


    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolderEmpatica holder;


            holder = new ViewHolderEmpatica();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.empatica_item_linechart, null);
            holder.chartAcc = convertView.findViewById(R.id.chartAcc_emp);
            holder.chartBvp = convertView.findViewById(R.id.chartBvp_emp);
            holder.chartHr = convertView.findViewById(R.id.chartHr_emp);


            convertView.setTag(holder);




        /** Estilos ACC**/
        holder.chartAcc.getDescription().setEnabled(false);
        holder.chartAcc.setDrawGridBackground(false);

        XAxis xAxis = holder.chartAcc.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chartAcc.getAxisLeft();

        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(-100f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);

        YAxis rightAxis = holder.chartAcc.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        // set data
        holder.chartAcc.setData((LineData) lineDataArrayList.get(0));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAcc.animateX(750);


        /** Estilos Bvp**/
        holder.chartBvp.getDescription().setEnabled(false);
        holder.chartBvp.setDrawGridBackground(false);

        XAxis xAxisAccl = holder.chartBvp.getXAxis();
        xAxisAccl.setPosition(XAxisPosition.BOTTOM);

        xAxisAccl.setDrawGridLines(false);
        xAxisAccl.setDrawAxisLine(true);

        YAxis leftAxisAccl = holder.chartBvp.getAxisLeft();

        leftAxisAccl.setLabelCount(5, false);
        leftAxisAccl.setAxisMinimum(-100f); // this replaces setStartAtZero(true)
        leftAxisAccl.setAxisMaximum(100f);

        YAxis rightAxisAccl = holder.chartBvp.getAxisRight();
        rightAxisAccl.setDrawLabels(false);
        rightAxisAccl.setDrawAxisLine(false);
        rightAxisAccl.setDrawGridLines(false);

        // set data
        holder.chartBvp.setData((LineData) lineDataArrayList.get(1));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartBvp.animateX(750);


        /** Estilos HR**/
        holder.chartHr.getDescription().setEnabled(false);
        holder.chartHr.setDrawGridBackground(false);

        XAxis xAxisGir = holder.chartHr.getXAxis();
        xAxisGir.setPosition(XAxisPosition.BOTTOM);

        xAxisGir.setDrawGridLines(false);
        xAxisGir.setDrawAxisLine(true);

        YAxis leftAxisGir = holder.chartHr.getAxisLeft();

        leftAxisGir.setLabelCount(5, false);
        leftAxisGir.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxisGir.setAxisMaximum(250f);

        YAxis rightAxisGir = holder.chartHr.getAxisRight();
        rightAxisGir.setDrawLabels(false);
        rightAxisGir.setDrawAxisLine(false);
        rightAxisGir.setDrawGridLines(false);

        // set data
        holder.chartHr.setData((LineData) lineDataArrayList.get(2));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartHr.animateX(750);



        return convertView;
    }

    private static class ViewHolderEmpatica {
        LineChart chartAcc;
        LineChart chartBvp;
        LineChart chartHr;

    }
}
