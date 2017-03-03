package maks.dev.diplom.Adapters.CurrencyFullInfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;

import maks.dev.diplom.R;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class AdapterCurrencyFullInfo
        extends RecyclerView.Adapter<ViewHolderCurrencyFull> {

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
        String name = currencyList.get(position).get("name").toString();
        holder.imgViewRecyclerView.setBackgroundResource(PreferenceUtils.getImageIdOfCurrency(name));
        holder.currencyName.setText(name);
        String tmpValue = currencyList.get(position).get("value").toString();
        String coefficient = currencyList.get(position).get("coefficient").toString();
        Double result = Double.parseDouble(tmpValue) * Double.parseDouble(coefficient);
        double roundedResult = new BigDecimal(result).setScale(2, RoundingMode.UP).doubleValue();
        holder.currencyValue.setText(String.valueOf(roundedResult));
        holder.currencyFullName.setText(currencyList.get(position).get("fullName").toString());
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
