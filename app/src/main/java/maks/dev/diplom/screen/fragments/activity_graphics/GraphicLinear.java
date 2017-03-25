package maks.dev.diplom.screen.fragments.activity_graphics;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maks.dev.diplom.screen.dialogs.DialogLoading;
import maks.dev.diplom.interfaces.GraphicResponseListener;
import maks.dev.diplom.R;
import maks.dev.diplom.data.network.GraphicResponse;

/**
 * Created by berezyckiy on 2/11/17.
 */

public class GraphicLinear
        extends Fragment
        implements GraphicResponseListener {

    private LineChart mChart;
    private View view;
    private String symbol;

    private ArrayList<Entry> values = new ArrayList<>();
    private ArrayList<Map<String, String>> symbolRates;
    private DialogLoading dialogLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.tab_graphic_linear, container, false);

        HashMap currencyOne = (HashMap) getActivity().getIntent().getSerializableExtra("firstCurrency");
        HashMap currencyTwo = (HashMap) getActivity().getIntent().getSerializableExtra("secondCurrency");
        String base = currencyOne.get("name").toString();
        symbol = currencyTwo.get("name").toString();
        dialogLoading = new DialogLoading();

        symbolRates = new ArrayList<>();
        new GraphicResponse(this, base, symbol).execute();
        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);

        mChart.getDescription().setEnabled(false);

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setPinchZoom(true);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        mChart.animateX(2500);

        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.LINE);

        return view;
    }

    private Float calculateMaxRate(ArrayList<Map<String, String>> data) {
        Float maximum = 0f;
        for (int i = 0; i < data.size(); i++) {
            if (Float.parseFloat(data.get(i).get(symbol)) > maximum) {
                maximum = Float.parseFloat(data.get(i).get(symbol));
            }
        }
        return maximum;
    }

    private void setData(ArrayList<Map<String, String>> myData) {
        final HashMap<Integer, String> numMap = new HashMap<>();
        for (int i = 0; i < myData.size(); i++) {
            float val = Float.parseFloat(myData.get(i).get(symbol));
            if (i < 10) {
                numMap.put(i, "200" + i);
            } else {
                numMap.put(i, "20" + i);
            }
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Dependency");

            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                if (getActivity() != null) {
                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.graphic_linear_fade_red);
                    set1.setFillDrawable(drawable);
                }
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            XAxis xAxis = mChart.getXAxis();
            YAxis yAxis = mChart.getAxisLeft();
            Float maxRate = calculateMaxRate(symbolRates);
            if (maxRate <= 1) {
                yAxis.setAxisMaximum(maxRate + 0.2f);
            } else {
                yAxis.setAxisMaximum(maxRate + 1);
            }
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return numMap.get((int) value);
                }
            });

            mChart.setData(data);
            mChart.invalidate();
        }
    }

    @Override
    public void onSuccessLoading(ArrayList<Map<String, String>> data) {
        symbolRates = data;
        setData(data);
    }

    @Override
    public void onErrorLoading() {
        Snackbar.make(view, "Error loading", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (!dialogLoading.isAdded()) {
            dialogLoading.show(getActivity().getSupportFragmentManager(), "DialogLoading");
        }
    }

    @Override
    public void hideProgressDialog() {
        dialogLoading.dismissAllowingStateLoss();
    }
}
