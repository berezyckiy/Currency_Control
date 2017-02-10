package maks.dev.diplom.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Adapters.CurrencyFullInfo.AdapterCurrencyFullInfo;
import maks.dev.diplom.Data.DB;
import maks.dev.diplom.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvMainScreen;
    private View view;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    static List<Map<String, Object>> currencyList;
    private AdapterCurrencyFullInfo mAdapter;
    private DB db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initItems();
        prepareData();
        addCurrencyList();
        buildRecyclerView();
        swipeRefresh.setOnRefreshListener(this);
        setDataOnTextView();
        return view;
    }

    private void setDataOnTextView() {
        if (getActivity().getIntent().getStringExtra("name") == null) {
            tvMainScreen.setText("USD 1");
        } else {
            String tmp = getActivity().getIntent().getStringExtra("name") + " " + getActivity().getIntent().getStringExtra("value");
            tvMainScreen.setText(tmp);
        }
    }

    private void initItems() {
        tvMainScreen = (TextView) view.findViewById(R.id.tvMainScreen);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        db = new DB(getContext());
        db.open();
        currencyList = new ArrayList<>();
        mAdapter = new AdapterCurrencyFullInfo(currencyList);
        MainActivity.nvView.setCheckedItem(R.id.nav_currency_exchange);
    }

    private void buildRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareData() {
        if (!db.isData()) {
            db.addRec("USD", 50, "United States", "true");
            db.addRec("EUR", 75, "England", "true");
            db.addRec("BYN", 25, "Belarussian Ruble", "true");
            db.addRec("GBP", 100, "Fund Sterlingov", "true");
            db.addRec("PLN", 70, "Polski Zlote", "true");
            db.addRec("RUR", 150, "Russian Ruble", "true");
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<Map<String, Object>> addCurrencyList() {
        Cursor tmpCursor = db.getAllData();
        Map<String, Object> tmpMap;
        if (tmpCursor.moveToFirst()) {
            do {
                if (Boolean.parseBoolean(tmpCursor.getString(tmpCursor.getColumnIndex("isChecked")))) {
                    tmpMap = new HashMap<>();
                    tmpMap.put("name", tmpCursor.getString(tmpCursor.getColumnIndex("name")));
                    tmpMap.put("value", tmpCursor.getString(tmpCursor.getColumnIndex("value")));
                    tmpMap.put("fullName", tmpCursor.getString(tmpCursor.getColumnIndex("fullName")));
                    tmpMap.put("isChecked", tmpCursor.getString(tmpCursor.getColumnIndex("isChecked")));
                    currencyList.add(tmpMap);
                }
            } while (tmpCursor.moveToNext());
        }
        db.close();
        return currencyList;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 3000);
    }
}
