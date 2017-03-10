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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Adapters.CurrencyFullInfo.AdapterCurrencyFullInfo;
import maks.dev.diplom.BuildConfig;
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

    private View view;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    static List<Map<String, Object>> currencyList;
    private List<Map<String, Object>> listWithoutMainCurrency;
    private AdapterCurrencyFullInfo mAdapter;
    private DB db;
    private DialogLoading dialogLoading;

    private String chosenCurrency;
    private String chosenSum;

    public interface CollapseListener {

        void enableCollapse(String base, String rate, String baseFullName);

        void disableCollapse();

        void disableTitle();

        void disableScrolling();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initItems();
        loadData();
        buildRecyclerView();
        return view;
    }

    private void loadData() {
        new CurrencyData(getActivity(), this).execute();
    }

    private String getFullNameChosenCurrency(String currencyName) {
        for (int i = 0; i < currencyList.size(); i++) {
            if (currencyList.get(i).get("name").equals(currencyName)) {
                return currencyList.get(i).get("fullName").toString();
            }
        }
        return "";
    }

    private void initItems() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        db = new DB(getContext());
        currencyList = new ArrayList<>();
        listWithoutMainCurrency = new ArrayList<>();
        mAdapter = new AdapterCurrencyFullInfo(listWithoutMainCurrency);
        MainActivity.nvView.setCheckedItem(R.id.nav_currency_exchange);
        chosenCurrency = getActivity().getIntent().getStringExtra("name");
        chosenSum = getActivity().getIntent().getStringExtra("sum");
        dialogLoading = new DialogLoading();
        if (chosenCurrency == null) {
            chosenCurrency = "USD";
            chosenSum = "1";
        }
        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle("");
        }
        ((CollapseListener) getActivity()).disableTitle();
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
        listWithoutMainCurrency.clear();
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
                    if (!tmpCursor.getString(tmpCursor.getColumnIndex("name")).equals(chosenCurrency)) {
                        listWithoutMainCurrency.add(tmpMap);
                    }
                }
            } while (tmpCursor.moveToNext());
        }
        db.close();

        mAdapter.notifyDataSetChanged();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(view, R.string.error_loading, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.button_error_loading, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.error_loading)
                        .content(R.string.reason_error_loading)
                        .positiveText(R.string.button_close).show();
            }
        });
        snackbar.show();
    }

    @Override
    public void onRefresh() {
        loadData();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSuccessLoadingData() {
        updateList();
        ((CollapseListener) getActivity()).enableCollapse(chosenCurrency, chosenSum,
                getFullNameChosenCurrency(chosenCurrency));
        //TODO sdelat` proverky kogda nechego pokazivat`

        if (listWithoutMainCurrency != null && listWithoutMainCurrency.size() <= 5) {
            ((CollapseListener) getActivity()).disableScrolling();
        }
    }

    @Override
    public void onErrorLoadingData() {
        updateList();
        ((CollapseListener) getActivity()).enableCollapse(chosenCurrency, chosenSum,
                getFullNameChosenCurrency(chosenCurrency));
//        Snackbar.make(view, getString(R.string.error_loading), Snackbar.LENGTH_SHORT).show();
        showSnackBar();
        //TODO sdelat` proverky kogda nechego pokazivat`

        if (listWithoutMainCurrency != null && listWithoutMainCurrency.size() <= 5) {
            ((CollapseListener) getActivity()).disableScrolling();
        }
    }

    @Override
    public void showProgressDialog() {
        dialogLoading.show(getActivity().getSupportFragmentManager(), "DialogLoading");
    }

    @Override
    public void hideProgressDialog() {
        dialogLoading.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((CollapseListener) getActivity()).disableCollapse();
    }
}
