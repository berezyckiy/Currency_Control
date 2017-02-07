package maks.dev.diplom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by berezyckiy on 2/7/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CurrencyHolder> {
    private List<CurrencyFactory.Currency> mCurrency;

    public RecyclerViewAdapter(List<CurrencyFactory.Currency> currencies) {
        mCurrency = currencies;
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {
        private TextView mCurrencyNameTv;
        private TextView mCurrencyValueTv;
        private TextView mCurrencyCountryTv;
        private CurrencyFactory.Currency mCurrency;

        public CurrencyHolder(View itemView) {
            super(itemView);
            mCurrencyNameTv = (TextView) itemView.findViewById(R.id.tvCurrencyName);
            mCurrencyValueTv = (TextView) itemView.findViewById(R.id.tvCurrencyValue);
            mCurrencyCountryTv = (TextView) itemView.findViewById(R.id.tvCurrencyFullName);
        }

        public void bindCrime(CurrencyFactory.Currency person) {
            mCurrency = person;
            mCurrencyNameTv.setText(mCurrency.getCurrencyName());
            mCurrencyValueTv.setText(String.valueOf(mCurrency.getCurrencyValue()));
            mCurrencyCountryTv.setText(mCurrency.getCurrencyCountry());
        }
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_currency, parent, false);
        return new CurrencyHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        CurrencyFactory.Currency person = mCurrency.get(position);
        holder.bindCrime(person);
    }

    @Override
    public int getItemCount() {
        return mCurrency.size();
    }
}
