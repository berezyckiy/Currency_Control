package maks.dev.diplom.available_currencies.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;
import java.util.Map;

import maks.dev.diplom.R;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class AdapterCurrencySelected
        extends RecyclerView.Adapter<ViewHolderCurrencySelected> {

    private List<Map<String, Object>> currencyList;
    private Activity mActivity;

    public AdapterCurrencySelected(@NonNull Activity activity, List<Map<String, Object>> currencyList) {
        this.currencyList = currencyList;
        mActivity = activity;
    }

    @Override
    public ViewHolderCurrencySelected onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_currency_selected, parent, false);
        return new ViewHolderCurrencySelected(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCurrencySelected holder, final int position) {
        String name = currencyList.get(position).get("name").toString();
        final Drawable img = mActivity.getResources().getDrawable(PreferenceUtils.getImageIdOfCurrency(name));
        img.setBounds(10, 0, 80, 48);
        final Drawable icon_on = mActivity.getResources().getDrawable(R.drawable.icon_on_normal);
        icon_on.setBounds(0, 0, 60, 50);
        final Drawable icon_off = mActivity.getResources().getDrawable(R.drawable.icon_off_normal);
        icon_off.setBounds(0, 0, 60, 60);
        if (Boolean.parseBoolean(currencyList.get(position).get("isChecked").toString())) {
            holder.button.setCompoundDrawables(img, null, icon_on, null);
        } else {
            holder.button.setCompoundDrawables(img, null, icon_off, null);
        }
        holder.button.setText(name);
        holder.button.setTextOn(name);
        holder.button.setTextOff(name);
        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    holder.button.setCompoundDrawables(img, null, icon_off, null);
                    currencyList.get(position).put("isChecked", "false");
                } else {
                    holder.button.setCompoundDrawables(img, null, icon_on, null);
                    currencyList.get(position).put("isChecked", "true");
                }
            }
        });
        holder.button.setChecked(Boolean.parseBoolean(currencyList.get(position).get("isChecked").toString()));
        if (holder.button.isChecked()) {
            holder.button.setCompoundDrawables(img, null, icon_on, null);
        } else {
            holder.button.setCompoundDrawables(img, null, icon_off, null);
        }
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
