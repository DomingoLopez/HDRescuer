package com.hdrescuer.hdrescuer.ui.ui.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.hdrescuer.hdrescuer.R;

import java.util.ArrayList;


public class TicWatchChartItem extends ChartItem {


    public TicWatchChartItem(ArrayList<LineData> cd, Context c) {
        super(cd);


    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.ticwatch_item_linechart, null);
            holder.chartAcc = convertView.findViewById(R.id.chartAcc);
            holder.chartAccl = convertView.findViewById(R.id.chartAccl);
            holder.chartGir = convertView.findViewById(R.id.chartGir);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling ACC
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

        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chartAcc.setData((LineData) mChartDataAcc);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAcc.animateX(750);


        //apply style ACCL
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

        rightAxisAccl.setLabelCount(5, false);
        rightAxisAccl.setDrawGridLines(false);
        rightAxisAccl.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chartAccl.setData((LineData) mChartDataAccl);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAccl.animateX(750);


        //apply style GIR
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

        rightAxisGir.setLabelCount(5, false);
        rightAxisGir.setDrawGridLines(false);
        rightAxisGir.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chartGir.setData((LineData) mChartDataGir);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartGir.animateX(750);

        return convertView;
    }

    private static class ViewHolder {
        LineChart chartAcc;
        LineChart chartAccl;
        LineChart chartGir;
    }
}
