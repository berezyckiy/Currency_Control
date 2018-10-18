package maks.dev.diplom.main_currency.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;
import maks.dev.diplom.model.db.DB;
import maks.dev.diplom.main_currency.ActivityChooseValue;
import maks.dev.diplom.main_currency.adapter.AdapterCurrencyNameInfo;
import maks.dev.diplom.main_currency.adapter.NameCurrencyListener;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseMainCurrency
        extends Fragment
        implements NameCurrencyListener {

    private RecyclerView recyclerViewChooseMainCurrency;
    private List<Map<String, Object>> currencyList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_currency, container, false);
        initItems();
        buildRecyclerView();
        fillDataCurrencyList();
        isCurrencyDataNull();
        return view;
    }

    private void initItems() {
        recyclerViewChooseMainCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseMainCurrency);
        currencyList = new ArrayList<>();
    }

    private void fillDataCurrencyList() {
        DB db = new DB(getContext());
        db.open();
        for (Map<String, Object> currency : db.getCurrenciesList()) {
            if (Boolean.parseBoolean(currency.get("isChecked").toString())) {
                currencyList.add(currency);
            }
        }
        db.close();
    }

    private void buildRecyclerView() {
        AdapterCurrencyNameInfo mAdapter = new AdapterCurrencyNameInfo(this, currencyList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewChooseMainCurrency.setLayoutManager(layoutManager);
        recyclerViewChooseMainCurrency.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChooseMainCurrency.setAdapter(mAdapter);
    }

    private void isCurrencyDataNull() {
        if (currencyList.size() == 0) {
            TextView tvNoCurrencySelected = (TextView) view.findViewById(R.id.tvNothingToShow);
            tvNoCurrencySelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startChooseValueActivity(String name, String value) {
        Integer currentTheme = PreferenceUtils.getInteger(getActivity(), "appTheme", R.style.AppTheme);
        Intent intent = new Intent(getActivity(), ActivityChooseValue.class);
        intent.putExtra("name", name);
        intent.putExtra("value", value);
        intent.putExtra("theme", currentTheme.toString());
        getActivity().startActivity(intent);
    }
}
