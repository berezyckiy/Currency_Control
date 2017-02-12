package maks.dev.diplom.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/11/17.
 */

public class GraphicLinear extends Fragment {

    private View view;
    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_graphic_linear, container, false);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        createLinearChart();
        return view;
    }

    private void createLinearChart() {
        List<Entry> valsComp1 = new ArrayList<>();
        List<Entry> valsComp2 = new ArrayList<>();
        Entry c1e1 = new Entry(0f, 100000f);
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(1f, 140000f);
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(2f, 120000f);
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(3f, 140000f);
        valsComp1.add(c1e4);

        Entry c2e1 = new Entry(0f, 130000f);
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(1f, 115000f);
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(2f, 90000f);
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(3f, 110000f);
        valsComp2.add(c2e4);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Complany 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(getResources().getColor(android.R.color.holo_red_dark));
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Complany 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        setComp2.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        final String[] quarters = new String[] { "Q1", "Q2", "Q3", "Q4" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();


    }
}
