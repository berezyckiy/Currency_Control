package maks.dev.diplom.Adapters.CurrencyNameInfo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityChooseValue.ActivityChooseValue;
import maks.dev.diplom.Interface.CurrencyDataListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class AdapterCurrencyNameInfo
        extends RecyclerView.Adapter<ViewHolderCurrencyName> {

    private List<Map<String, Object>> currencyList;
    private NameCurrencyListener mListener;

    public AdapterCurrencyNameInfo(@NonNull NameCurrencyListener listener, List<Map<String, Object>> currencyList) {
        this.currencyList = currencyList;
        mListener = listener;
    }

    @Override
    public ViewHolderCurrencyName onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency_name, parent, false);
        return new ViewHolderCurrencyName(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCurrencyName holder, final int position) {
        holder.tvSoloCurrencyName.setText(currencyList.get(position).get("name").toString());
        holder.tvSoloCurrencyName.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.tvSoloCurrencyName.getText().toString();
                String value = currencyList.get(position).get("value").toString();
                mListener.startChooseValueActivity(name, value);

            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
