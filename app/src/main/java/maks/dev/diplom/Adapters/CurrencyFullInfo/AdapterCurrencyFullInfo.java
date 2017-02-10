package maks.dev.diplom.Adapters.CurrencyFullInfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class AdapterCurrencyFullInfo extends RecyclerView.Adapter<ViewHolderCurrencyFull> {
    private List<Map<String, Object>> currencyList;

    public AdapterCurrencyFullInfo(List<Map<String, Object>> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public ViewHolderCurrencyFull onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency_full, parent, false);
        return new ViewHolderCurrencyFull(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderCurrencyFull holder, int position) {
        holder.currencyName.setText(currencyList.get(position).get("name").toString());
        holder.currencyValue.setText(String.valueOf(currencyList.get(position).get("value")));
        holder.currencyFullName.setText(currencyList.get(position).get("fullName").toString());
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
