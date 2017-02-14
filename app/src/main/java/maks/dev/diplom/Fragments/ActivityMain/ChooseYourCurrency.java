package maks.dev.diplom.Fragments.ActivityMain;

import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Adapters.CurrencySelected.AdapterCurrencySelected;
import maks.dev.diplom.Data.DB;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseYourCurrency extends Fragment {

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
        return view;
    }

    private void initItems() {
        db = new DB(getActivity());
        recyclerViewChooseYourCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseYourCurrency);
        currencyList = getAllCurrencyFromDB();
        MainActivity.nvView.setCheckedItem(R.id.nav_choose_your_currency);
    }

    private void buildRecyclerView() {
        mAdapter = new AdapterCurrencySelected(currencyList);
        recyclerViewChooseYourCurrency.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChooseYourCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChooseYourCurrency.setAdapter(mAdapter);
    }

    private List<Map<String, Object>> getAllCurrencyFromDB() {
        db.open();
        Cursor tmpCursor = db.getAllData();
        currencyList = new ArrayList<>();
        Map<String, Object> tmpMap;
        if (tmpCursor.moveToFirst()) {
            do {
                tmpMap = new HashMap<>();
                tmpMap.put("name", tmpCursor.getString(tmpCursor.getColumnIndex("name")));
                tmpMap.put("value", tmpCursor.getString(tmpCursor.getColumnIndex("value")));
                tmpMap.put("fullName", tmpCursor.getString(tmpCursor.getColumnIndex("fullName")));
                tmpMap.put("isChecked", tmpCursor.getString(tmpCursor.getColumnIndex("isChecked")));
                currencyList.add(tmpMap);
            } while (tmpCursor.moveToNext());
        }
        db.close();
        return currencyList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_btnsubmit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnDone:
                db.open();
                db.delAllData();
                for (int i = 0; i < currencyList.size(); i++) {
                    String name = currencyList.get(i).get("name").toString();
                    double value = Double.parseDouble(currencyList.get(i).get("value").toString());
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
