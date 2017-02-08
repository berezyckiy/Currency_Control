package maks.dev.diplom.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import java.util.List;

import maks.dev.diplom.Currency;
import maks.dev.diplom.CurrencyAdapter;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvMainScreen;
    private View view;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private List<Currency> currencyList;
    private CurrencyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initItems();
        buildRecyclerView();
        prepareCurrencyData();
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
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        currencyList = new ArrayList<>();
    }

    private void buildRecyclerView() {
        mAdapter = new CurrencyAdapter(currencyList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareCurrencyData() {
        Currency currency = new Currency("USD", 100, "United States");
        currencyList.add(currency);

        currency = new Currency("EUR", 100, "England");
        currencyList.add(currency);

        currency = new Currency("RUR", 100, "Russian Ruble");
        currencyList.add(currency);

        mAdapter.notifyDataSetChanged();
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
