package maks.dev.diplom.Adapters.CurrencyNameInfo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityChooseValue.ActivityChooseValue;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class AdapterCurrencyNameInfo
        extends RecyclerView.Adapter<ViewHolderCurrencyName> {

    private List<Map<String, Object>> currencyNamesList;

    public AdapterCurrencyNameInfo(List<Map<String, Object>> currencyNamesList) {
        this.currencyNamesList = currencyNamesList;
    }

    @Override
    public ViewHolderCurrencyName onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency_name, parent, false);
        return new ViewHolderCurrencyName(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCurrencyName holder, int position) {
        holder.tvSoloCurrencyName.setText(currencyNamesList.get(position).get("name").toString());
        holder.tvSoloCurrencyName.setOnClickListener(new RecyclerView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityChooseValue.class);
                intent.putExtra("name", holder.tvSoloCurrencyName.getText());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyNamesList.size();
    }
}
