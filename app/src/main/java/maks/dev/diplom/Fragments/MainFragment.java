package maks.dev.diplom.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import maks.dev.diplom.CurrencyFactory;
import maks.dev.diplom.R;
import maks.dev.diplom.RecyclerViewAdapter;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        initItems();
        buildRecycleView();
        swipeRefresh.setOnRefreshListener(this);
        return view;
    }

    private void initItems() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
    }

    private void buildRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<CurrencyFactory.Currency> currencyList = CurrencyFactory.getCurrencyList();
        mAdapter = new RecyclerViewAdapter(currencyList);
        mRecyclerView.setAdapter(mAdapter);
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
