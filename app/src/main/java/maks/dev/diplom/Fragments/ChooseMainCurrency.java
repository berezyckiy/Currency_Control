package maks.dev.diplom.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.Adapters.CurrencyNameInfo.AdapterCurrencyNameInfo;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseMainCurrency extends Fragment {

    private RecyclerView recyclerViewChooseMainCurrency;
    private AdapterCurrencyNameInfo mAdapter;
    private List<Map<String, Object>> currencyList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_currency, container, false);
        initItems();
        buildRecyclerView();
        return view;
    }

    private void initItems() {
        recyclerViewChooseMainCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseMainCurrency);
        currencyList = MainFragment.currencyList;
    }

    private void buildRecyclerView() {
        mAdapter = new AdapterCurrencyNameInfo(currencyList);
        recyclerViewChooseMainCurrency.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChooseMainCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChooseMainCurrency.setAdapter(mAdapter);
    }
}
