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

    ArrayList<LineData> lineDataArrayList;

    ChartItem(ArrayList<LineData> cd) {
        this.lineDataArrayList = cd;
    }

    public abstract int getItemType();

    public abstract View getView(int position, View convertView, Context c);
}
