package maks.dev.diplom.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import maks.dev.diplom.Currency;
import maks.dev.diplom.CurrencyAdapter;
import maks.dev.diplom.CurrencyNameAdapter;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseMainCurrency extends Fragment {

    private RecyclerView recyclerViewChooseMainCurrency;
    private CurrencyNameAdapter mAdapter;
    private List<Currency> currencyNamesList;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_currency, container, false);
        initItems();
        buildRecyclerView();
        prepareCurrencyData();
        return view;
    }

    private void initItems() {
        recyclerViewChooseMainCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseMainCurrency);
        currencyNamesList = new ArrayList<>();
    }

    private void buildRecyclerView() {
        mAdapter = new CurrencyNameAdapter(currencyNamesList);
        recyclerViewChooseMainCurrency.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChooseMainCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChooseMainCurrency.setAdapter(mAdapter);
    }

    private void prepareCurrencyData() {
        Currency currency = new Currency("USD");
        currencyNamesList.add(currency);

        currency = new Currency("EUR");
        currencyNamesList.add(currency);

        currency = new Currency("RUR");
        currencyNamesList.add(currency);

        currency = new Currency("BYN");
        currencyNamesList.add(currency);

        currency = new Currency("PLN");
        currencyNamesList.add(currency);

        currency = new Currency("TG");
        currencyNamesList.add(currency);

        currency = new Currency("GBP");
        currencyNamesList.add(currency);

        mAdapter.notifyDataSetChanged();
    }
}
