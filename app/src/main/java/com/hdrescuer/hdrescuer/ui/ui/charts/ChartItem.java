package com.hdrescuer.hdrescuer.ui.ui.charts;



import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

/**
 * Base class of the Chart ListView items
 * @author philipp
 *
 */
@SuppressWarnings("unused")
public abstract class ChartItem {

    static final int TYPE_BARCHART = 0;
    static final int TYPE_LINECHART = 1;
    static final int TYPE_PIECHART = 2;

    ChartData<?> mChartDataAcc;
    ChartData<?> mChartDataAccl;
    ChartData<?> mChartDataGir;

    ChartItem(ArrayList<LineData> cd) {
        this.mChartDataAcc = cd.get(0);
        this.mChartDataAccl = cd.get(1);
        this.mChartDataGir = cd.get(2);

    }

    public abstract int getItemType();

    public abstract View getView(int position, View convertView, Context c);
}
