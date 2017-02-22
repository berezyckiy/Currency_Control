package maks.dev.diplom.Fragments.ActivityMain;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Adapters.CurrencyFullInfo.AdapterCurrencyFullInfo;
import maks.dev.diplom.Data.DB;
import maks.dev.diplom.Dialogs.DialogLoading;
import maks.dev.diplom.Interface.CurrencyDataListener;
import maks.dev.diplom.R;
import maks.dev.diplom.network.CurrencyData;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class MainFragment
        extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, CurrencyDataListener {

    private TextView tvMainScreen;
    private View view;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    static List<Map<String, Object>> currencyList;
    private AdapterCurrencyFullInfo mAdapter;
    private DB db;
    private DialogLoading dialogLoading;

    private String chosenCurrency;
    private String chosenSum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initItems();
        loadData();
        setChosenNameAndSum();
        buildRecyclerView();
        return view;
    }

    private void loadData() {
        new CurrencyData(getActivity(), this).execute();
    }

    private void setChosenNameAndSum() {
        if (chosenCurrency == null) {
            tvMainScreen.setText("USD 1");
            chosenCurrency = "USD";
            chosenSum = "1";
        } else {
            tvMainScreen.setText(chosenCurrency + " " + chosenSum);
        }
    }

    private void initItems() {
        tvMainScreen = (TextView) view.findViewById(R.id.tvMainScreen);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        db = new DB(getContext());
        currencyList = new ArrayList<>();
        mAdapter = new AdapterCurrencyFullInfo(currencyList);
        MainActivity.nvView.setCheckedItem(R.id.nav_currency_exchange);
        chosenCurrency = getActivity().getIntent().getStringExtra("name");
        chosenSum = getActivity().getIntent().getStringExtra("sum");
        dialogLoading = new DialogLoading();
    }


    private void buildRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private double calculateCoefficient() {
        String value = getActivity().getIntent().getStringExtra("value");
        if (value == null) {
            value = "1";
        }
        return Double.parseDouble(chosenSum) / Double.parseDouble(value);
    }

    private void updateList() {
        db.open();
        currencyList.clear();
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
                    tmpMap.put("coefficient", calculateCoefficient());
                    currencyList.add(tmpMap);
                }
            } while (tmpCursor.moveToNext());
        }
        db.close();
        isNothingToShow(currencyList.size() == 0);
        mAdapter.notifyDataSetChanged();
    }

    private void isNothingToShow(boolean result) {
        if (result) {
            tvMainScreen.setText(R.string.add_currency_please);
        } else {
            tvMainScreen.setText(tvMainScreen.getText().toString());
        }
    }

    @Override
    public void onRefresh() {
        loadData();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSuccessLoadingData() {
        updateList();
    }

    @Override
    public void onErrorLoadingData() {
        updateList();
        Snackbar.make(view, "Error loading data", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        dialogLoading.show(getActivity().getSupportFragmentManager(), "DialogLoading");
    }

    @Override
    public void hideProgressBar() {
        dialogLoading.dismissAllowingStateLoss();
    }
}
