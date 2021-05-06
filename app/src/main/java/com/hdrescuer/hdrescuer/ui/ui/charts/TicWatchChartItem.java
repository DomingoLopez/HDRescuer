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


public class TicWatchChartItem extends ChartItem {

    int steps;

    public TicWatchChartItem(ArrayList<LineData> cd, int steps, Context c) {
        super(cd);

        this.steps = steps;

    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolderTicWatch holder;

            holder = new ViewHolderTicWatch();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.ticwatch_item_linechart, null);
            holder.chartAcc = convertView.findViewById(R.id.chartAcc_tic);
            holder.chartAccl = convertView.findViewById(R.id.chartAccl_tic);
            holder.chartGir = convertView.findViewById(R.id.chartGir_tic);
            holder.chartHr = convertView.findViewById(R.id.chartHr_tic);
            holder.steps = convertView.findViewById(R.id.tvPasosSesionTic);

            convertView.setTag(holder);



        /**Pasos*/
        holder.steps.setText(Integer.toString(this.steps));

        /** Estilos ACC**/
        holder.chartAcc.getDescription().setEnabled(false);
        holder.chartAcc.setDrawGridBackground(false);

        XAxis xAxis = holder.chartAcc.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chartAcc.getAxisLeft();

        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(-50f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(50f);

        YAxis rightAxis = holder.chartAcc.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        // set data
        holder.chartAcc.setData((LineData) lineDataArrayList.get(0));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAcc.animateX(750);


        /** Estilos ACCl**/
        holder.chartAccl.getDescription().setEnabled(false);
        holder.chartAccl.setDrawGridBackground(false);

        XAxis xAxisAccl = holder.chartAccl.getXAxis();
        xAxisAccl.setPosition(XAxisPosition.BOTTOM);

        xAxisAccl.setDrawGridLines(false);
        xAxisAccl.setDrawAxisLine(true);

        YAxis leftAxisAccl = holder.chartAccl.getAxisLeft();

        leftAxisAccl.setLabelCount(5, false);
        leftAxisAccl.setAxisMinimum(-50f); // this replaces setStartAtZero(true)
        leftAxisAccl.setAxisMaximum(50f);

        YAxis rightAxisAccl = holder.chartAccl.getAxisRight();
        rightAxisAccl.setDrawLabels(false);
        rightAxisAccl.setDrawAxisLine(false);
        rightAxisAccl.setDrawGridLines(false);

        // set data
        holder.chartAccl.setData((LineData) lineDataArrayList.get(1));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAccl.animateX(750);


        /** Estilos GIR**/
        holder.chartGir.getDescription().setEnabled(false);
        holder.chartGir.setDrawGridBackground(false);

        XAxis xAxisGir = holder.chartGir.getXAxis();
        xAxisGir.setPosition(XAxisPosition.BOTTOM);

        xAxisGir.setDrawGridLines(false);
        xAxisGir.setDrawAxisLine(true);

        YAxis leftAxisGir = holder.chartGir.getAxisLeft();

        leftAxisGir.setLabelCount(5, false);
        leftAxisGir.setAxisMinimum(-50f); // this replaces setStartAtZero(true)
        leftAxisGir.setAxisMaximum(50f);

        YAxis rightAxisGir = holder.chartGir.getAxisRight();
        rightAxisGir.setDrawLabels(false);
        rightAxisGir.setDrawAxisLine(false);
        rightAxisGir.setDrawGridLines(false);

        // set data
        holder.chartGir.setData((LineData) lineDataArrayList.get(2));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartGir.animateX(750);

        /** Estilos HR**/
        holder.chartHr.getDescription().setEnabled(false);
        holder.chartHr.setDrawGridBackground(false);

        XAxis xAxisHr = holder.chartHr.getXAxis();
        xAxisHr.setPosition(XAxisPosition.BOTTOM);

        xAxisHr.setDrawGridLines(false);
        xAxisHr.setDrawAxisLine(true);

        YAxis leftAxisHr = holder.chartHr.getAxisLeft();

        leftAxisHr.setLabelCount(5, false);
        leftAxisHr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxisHr.setAxisMaximum(250f);

        YAxis rightAxisHr = holder.chartHr.getAxisRight();
        rightAxisHr.setDrawLabels(false);
        rightAxisHr.setDrawAxisLine(false);
        rightAxisHr.setDrawGridLines(false);

        // set data
        holder.chartHr.setData((LineData) lineDataArrayList.get(3));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartHr.animateX(750);

        return convertView;
    }

    private static class ViewHolderTicWatch {
        LineChart chartAcc;
        LineChart chartAccl;
        LineChart chartGir;
        LineChart chartHr;

        TextView steps;
    }
}
