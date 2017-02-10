package maks.dev.diplom.Adapters.CurrencySelected;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class AdapterCurrencySelected extends RecyclerView.Adapter<ViewHolderCurrencySelected> {
    private List<Map<String, Object>> currencyList;

    public AdapterCurrencySelected(List<Map<String, Object>> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public ViewHolderCurrencySelected onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency_selected, parent, false);
        return new ViewHolderCurrencySelected(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderCurrencySelected holder, final int position) {
        holder.tvCurrencySelectedName.setText(currencyList.get(position).get("name").toString());
        holder.cbCurrencySelected.setChecked(Boolean.parseBoolean(currencyList.get(position).get("isChecked").toString()));
        holder.cbCurrencySelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currencyList.get(position).put("isChecked", String.valueOf(isChecked));
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
