package maks.dev.diplom.screen.fragments.activity_main;

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

import java.util.List;
import java.util.Map;

import maks.dev.diplom.screen.activities.ActivityChooseValue;
import maks.dev.diplom.screen.activities.MainActivity;
import maks.dev.diplom.screen.adapters.currency_name_info.AdapterCurrencyNameInfo;
import maks.dev.diplom.screen.adapters.currency_name_info.NameCurrencyListener;
import maks.dev.diplom.R;
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
        isCurrencyDataNull();
        return view;
    }

    private void initItems() {
        recyclerViewChooseMainCurrency = (RecyclerView) view.findViewById(R.id.recyclerViewChooseMainCurrency);
        currencyList = MainFragment.currencyList;
        MainActivity.nvView.setCheckedItem(R.id.nav_choose_main_currency);
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
