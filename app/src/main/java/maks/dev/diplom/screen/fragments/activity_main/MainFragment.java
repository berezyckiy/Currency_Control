package maks.dev.diplom.screen.fragments.activity_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import maks.dev.diplom.R;
import maks.dev.diplom.data.db.DB;
import maks.dev.diplom.data.network.CurrencyData;
import maks.dev.diplom.interfaces.CurrencyDataListener;
import maks.dev.diplom.screen.adapters.currency_full_info.AdapterCurrencyFullInfo;
import maks.dev.diplom.screen.dialogs.DialogLoading;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/6/17.
 */

public class MainFragment
        extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, CurrencyDataListener {

    private View view;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private List<Map<String, Object>> currencyList;
    private AdapterCurrencyFullInfo mAdapter;
    private DB db;
    private DialogLoading dialogLoading;
    private TextView tvNothingToShow;

    private String chosenCurrency;
    private String chosenSum;
    private String dateOfSuccessfulUpdate;
    private String value;

    public interface CollapseListener {

        void enableCollapse(String base, String rate, String baseFullName, String value);

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
        if (isUpdateData()) {
            new CurrencyData(getActivity(), this).execute();
        }
        updateList();
    }

    private boolean isUpdateData() {
        if (getActivity() != null) {
            dateOfSuccessfulUpdate = PreferenceUtils.getString(getActivity(), "dateOfSuccessfulUpdate", "2017-02-04:00");
        }
        Integer hourOfUpdate = Integer.parseInt(dateOfSuccessfulUpdate.substring
                (dateOfSuccessfulUpdate.length() - 2, dateOfSuccessfulUpdate.length()));
        Integer dayOfUpdate = Integer.parseInt(dateOfSuccessfulUpdate.substring
                (dateOfSuccessfulUpdate.length() - 5, dateOfSuccessfulUpdate.length() - 3));
        int daysAfterUpdate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - dayOfUpdate;
        if (daysAfterUpdate > 1) {
            return true;
        } else if (daysAfterUpdate == 1) {
            if (hourOfUpdate < 20) {
                return true;
            } else if (hourOfUpdate > 20 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 20) {
                return true;
            }
        } else if (daysAfterUpdate == 0) {
            if (hourOfUpdate < 20 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 20) {
                return true;
            }
        }
        return false;
    }

    private void initItems() {
        tvNothingToShow = (TextView) view.findViewById(R.id.tvNothingToShow);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        db = new DB(getContext());
        currencyList = new ArrayList<>();
        mAdapter = new AdapterCurrencyFullInfo(currencyList);
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
        value = getActivity().getIntent().getStringExtra("value");
        if (value == null) {
            value = "1";
        }
        if (chosenSum != null && Double.parseDouble(chosenSum) == 0) {
            chosenSum = "1";
        }
        return Double.parseDouble(chosenSum) / Double.parseDouble(value);
    }

    private void updateList() {
        db.open();
        currencyList.clear();
        for (Map<String, Object> currency : db.getCurrenciesList()) {
            if (Boolean.parseBoolean(currency.get("isChecked").toString())) {
                if (!currency.get("name").equals(chosenCurrency)) {
                    currency.put("coefficient", calculateCoefficient());
                    currencyList.add(currency);
                }
            }
        }
        db.close();
        mAdapter.notifyDataSetChanged();

        if (isChosenCurrenciesLessThenTwo()) {
            currencyList.clear();
            mAdapter.notifyDataSetChanged();
            if (getActivity() != null) {
                ((CollapseListener) getActivity()).disableCollapse();
                tvNothingToShow.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (tvNothingToShow.getVisibility() == View.VISIBLE) {
            tvNothingToShow.setVisibility(View.GONE);
        }
        if (getActivity() != null) {
            ((CollapseListener) getActivity()).enableCollapse(chosenCurrency, chosenSum,
                    PreferenceUtils.getFullNameOfCurrency(chosenCurrency), value);
        }
        if (currencyList != null && currencyList.size() <= 5) {
            ((CollapseListener) getActivity()).disableScrolling();
        }
    }

    private boolean isChosenCurrenciesLessThenTwo() {
        ArrayList<Map<String, Object>> currenciesList = new ArrayList<>();
        db.open();
        for (Map<String, Object> currency : db.getCurrenciesList()) {
            if (Boolean.parseBoolean(currency.get("isChecked").toString())) {
                currenciesList.add(currency);
            }
        }
        db.close();
        return currenciesList.size() < 2;
    }

    private void showSnackBar() {
        if (view.isShown()) {
            Snackbar snackbar = Snackbar.make(view, R.string.error_loading, Snackbar.LENGTH_LONG);
            if (!snackbar.isShownOrQueued()) {
                snackbar.setAction(R.string.button_error_loading, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(getActivity())
                                .title(R.string.error_loading)
                                .content(R.string.reason_error_loading)
                                .positiveText(R.string.button_close).show();
                    }
                });
            }
            snackbar.show();
        }
    }

    @Override
    public void onRefresh() {
        loadData();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSuccessLoadingData() {
        updateList();
        DateFormat myDate = new SimpleDateFormat("yyyy-MM-dd:HH", Locale.ENGLISH);
        String currentDate = myDate.format(Calendar.getInstance().getTime());
        PreferenceUtils.saveString(getActivity(), "dateOfSuccessfulUpdate", currentDate);
    }

    @Override
    public void onErrorLoadingData() {
        updateList();
        showSnackBar();
    }

    @Override
    public void showProgressDialog() {
        if (!dialogLoading.isAdded()) {
            dialogLoading.show(getActivity().getSupportFragmentManager(), "DialogLoading");
        }
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
