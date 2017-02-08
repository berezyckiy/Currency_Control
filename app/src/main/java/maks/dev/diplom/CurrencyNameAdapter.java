package maks.dev.diplom;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class CurrencyNameAdapter extends RecyclerView.Adapter<CurrencyNameAdapter.MyViewHolder> {
    private List<Currency> currencyNamesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSoloCurrencyName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvSoloCurrencyName = (TextView) itemView.findViewById(R.id.tvSoloCurrencyName);
        }
    }

    public CurrencyNameAdapter(List<Currency> currencyNamesList) {
        this.currencyNamesList = currencyNamesList;
    }

    @Override
    public CurrencyNameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_solo_currency, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrencyNameAdapter.MyViewHolder holder, int position) {
        Currency currency = currencyNamesList.get(position);
        holder.tvSoloCurrencyName.setText(currency.getName());
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
