package maks.dev.diplom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {
    private List<Currency> currencyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView currencyName;
        private TextView currencyValue;
        private TextView currencyFullName;

        public MyViewHolder(View itemView) {
            super(itemView);
            currencyName = (TextView) itemView.findViewById(R.id.tvCurrencyName);
            currencyValue = (TextView) itemView.findViewById(R.id.tvCurrencyValue);
            currencyFullName = (TextView) itemView.findViewById(R.id.tvCurrencyFullName);
        }
    }

    public CurrencyAdapter(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.currencyName.setText(currency.getName());
        holder.currencyValue.setText(String.valueOf(currency.getValue()));
        holder.currencyFullName.setText(currency.getFullName());
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
