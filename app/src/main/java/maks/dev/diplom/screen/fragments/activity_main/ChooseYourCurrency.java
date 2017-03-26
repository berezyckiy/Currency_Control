package maks.dev.diplom.screen.fragments.activity_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;
import maks.dev.diplom.data.db.DB;
import maks.dev.diplom.screen.adapters.currency_selected.AdapterCurrencySelected;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseYourCurrency
        extends Fragment {

    private RecyclerView recyclerViewChooseYourCurrency;
    private AdapterCurrencySelected mAdapter;
    private List<Map<String, Object>> currencyList;
    private View view;
    private DB db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_your_currency, container, false);
        initItems();
        buildRecyclerView();
        getAllCurrenciesFromDB();
        whenNoCurrencies();
        return view;
    }

    private void initItems() {
        db = new DB(getActivity());
        recyclerViewChooseYourCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseYourCurrency);
        currencyList = new ArrayList<>();
    }

    private void buildRecyclerView() {
        mAdapter = new AdapterCurrencySelected(getActivity(), currencyList);
        recyclerViewChooseYourCurrency.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChooseYourCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChooseYourCurrency.setAdapter(mAdapter);
    }

    private void getAllCurrenciesFromDB() {
        db.open();
        for (Map<String, Object> currency : db.getCurrenciesList()) {
            currencyList.add(currency);
        }
        db.close();
    }

    private void whenNoCurrencies() {
        if (currencyList.size() == 0) {
            TextView tvNoCurrencySelected = (TextView) view.findViewById(R.id.tvNothingToShow);
            tvNoCurrencySelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemDone = menu.findItem(R.id.btnDone);
        if (PreferenceUtils.getInteger(getActivity(), "appTheme", R.style.AppTheme) != R.style.AppTheme) {
            itemDone.setIcon(R.mipmap.ic_menu_done_white);
        } else {
            itemDone.setIcon(R.mipmap.ic_menu_done);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_choose_your_currency, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnCheckAll:
                for (int i = 0; i < currencyList.size(); i++) {
                    currencyList.get(i).put("isChecked", "true");
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btnUnCheckAll:
                for (int i = 0; i < currencyList.size(); i++) {
                    currencyList.get(i).put("isChecked", "false");
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btnDone:
                db.open();
                db.delAllData();
                for (int i = 0; i < currencyList.size(); i++) {
                    String name = currencyList.get(i).get("name").toString();
                    String value = currencyList.get(i).get("value").toString();
                    String fullName = currencyList.get(i).get("fullName").toString();
                    String isChecked = currencyList.get(i).get("isChecked").toString();
                    db.addRec(name, value, fullName, isChecked);
                }
                db.close();
                startMainFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startMainFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contentFrame, new MainFragment()).commit();
    }
}
