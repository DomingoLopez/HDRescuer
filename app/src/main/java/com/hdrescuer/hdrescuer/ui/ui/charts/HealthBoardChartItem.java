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


public class HealthBoardChartItem extends ChartItem {

    private int minBlood;
    private int maxBlood;
    private int averageBlood;

    public HealthBoardChartItem(ArrayList<LineData> cd, int minBlood, int maxBlood, int averageBlood, Context c) {
        super(cd);

        this.minBlood = minBlood;
        this.maxBlood = maxBlood;
        this.averageBlood = averageBlood;

    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolderHealthBoard holder;

            holder = new ViewHolderHealthBoard();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.healthboard_item_linechart, null);
            holder.chartHr = convertView.findViewById(R.id.chartHR_HealthBoard);
            holder.chartAirFlow = convertView.findViewById(R.id.chartAirFlow_HealthBoard);

            holder.minBlood = convertView.findViewById(R.id.tvminblood);
            holder.maxBlood = convertView.findViewById(R.id.tvmaxblood);
            holder.averageBlood = convertView.findViewById(R.id.tvaverageblood);


            convertView.setTag(holder);


        /** Estilos para los TextView**/
        holder.minBlood.setText(Integer.toString(this.minBlood) + "%");
        holder.maxBlood.setText(Integer.toString(this.maxBlood) + "%");
        holder.averageBlood.setText(Integer.toString(this.averageBlood) + "%");


        /** Estilos AHR**/
        holder.chartHr.getDescription().setEnabled(false);
        holder.chartHr.setDrawGridBackground(false);

        XAxis xAxis = holder.chartHr.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chartHr.getAxisLeft();

        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(250);

        YAxis rightAxis = holder.chartHr.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        // set data
        holder.chartHr.setData((LineData) lineDataArrayList.get(0));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartHr.animateX(750);


        /** Estilos AirFlow**/
        holder.chartAirFlow.getDescription().setEnabled(false);
        holder.chartAirFlow.setDrawGridBackground(false);

        XAxis xAxisAF = holder.chartAirFlow.getXAxis();
        xAxisAF.setPosition(XAxisPosition.BOTTOM);

        xAxisAF.setDrawGridLines(false);
        xAxisAF.setDrawAxisLine(true);

        YAxis leftAxisAF = holder.chartAirFlow.getAxisLeft();

        leftAxisAF.setLabelCount(5, false);
        leftAxisAF.setAxisMinimum(0); // this replaces setStartAtZero(true)
        leftAxisAF.setAxisMaximum(400);

        YAxis rightAxisAF = holder.chartAirFlow.getAxisRight();
        rightAxisAF.setDrawLabels(false);
        rightAxisAF.setDrawAxisLine(false);
        rightAxisAF.setDrawGridLines(false);

        // set data
        holder.chartAirFlow.setData((LineData) lineDataArrayList.get(1));

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chartAirFlow.animateX(750);



        return convertView;
    }

    private static class ViewHolderHealthBoard {
        LineChart chartHr;
        LineChart chartAirFlow;
        TextView maxBlood;
        TextView minBlood;
        TextView averageBlood;

    }
}
