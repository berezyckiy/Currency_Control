package maks.dev.diplom.Fragments.ActivityMain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityChooseValue.ActivityChooseValue;
import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Adapters.CurrencyNameInfo.AdapterCurrencyNameInfo;
import maks.dev.diplom.Adapters.CurrencyNameInfo.NameCurrencyListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class ChooseMainCurrency
        extends Fragment
        implements NameCurrencyListener {

    private RecyclerView recyclerViewChooseMainCurrency;
    private List<Map<String, Object>> currencyList;
    private View view;
    private LinearLayout linLayoutMainCurrency;

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
        linLayoutMainCurrency = (LinearLayout) view.findViewById(R.id.linLayoutMainCurrency);
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
            TextView textView = new TextView(getActivity());
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setText(getString(R.string.no_currency_is_selected));
            textView.setTextColor(Color.WHITE);
            linLayoutMainCurrency.addView(textView, lParams);
        }
    }

    @Override
    public void startChooseValueActivity(String name, String value) {
        Intent intent = new Intent(getActivity(), ActivityChooseValue.class);
        intent.putExtra("name", name);
        intent.putExtra("value", value);
        getActivity().startActivity(intent);
    }
}
