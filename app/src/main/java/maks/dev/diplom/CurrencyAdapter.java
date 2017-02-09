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

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<Currency> currencyList;
    private int mViewSetType;

    private final int viewTypeName = 0;
    private final int viewTypeFull = 1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolderName extends ViewHolder {
        private TextView currencyName;

        public MyViewHolderName(View itemView) {
            super(itemView);
            currencyName = (TextView) itemView.findViewById(R.id.tvCurrencyOnlyName);
        }
    }

    public class MyViewHolderFull extends ViewHolder {
        private TextView currencyName;
        private TextView currencyValue;
        private TextView currencyFullName;

        public MyViewHolderFull(View itemView) {
            super(itemView);
            currencyName = (TextView) itemView.findViewById(R.id.tvCurrencyName);
            currencyValue = (TextView) itemView.findViewById(R.id.tvCurrencyValue);
            currencyFullName = (TextView) itemView.findViewById(R.id.tvCurrencyFullName);
        }
    }

    public CurrencyAdapter(List<Currency> currencyList, int mViewSetType) {
        this.currencyList = currencyList;
        this.mViewSetType = mViewSetType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case viewTypeName:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_currency_name, parent, false);
                return new MyViewHolderName(view);
            case viewTypeFull:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_currency_full, parent, false);
                return new MyViewHolderFull(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case viewTypeName:
                final MyViewHolderName myViewHolderName = (MyViewHolderName) holder;
                myViewHolderName.currencyName.setText(currencyList.get(position).getName());
                myViewHolderName.currencyName.setOnClickListener(new RecyclerView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ActivityChooseValue.class);
                        intent.putExtra("name", myViewHolderName.currencyName.getText());
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            case viewTypeFull:
                MyViewHolderFull myViewHolderFull = (MyViewHolderFull) holder;
                myViewHolderFull.currencyName.setText(currencyList.get(position).getName());
                myViewHolderFull.currencyValue.setText(String.valueOf(currencyList.get(position).getValue()));
                myViewHolderFull.currencyFullName.setText(currencyList.get(position).getFullName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewSetType;
    }
}
