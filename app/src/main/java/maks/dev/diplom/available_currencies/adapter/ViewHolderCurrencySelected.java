package maks.dev.diplom.available_currencies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ToggleButton;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

class ViewHolderCurrencySelected
        extends RecyclerView.ViewHolder {

    ToggleButton button;

    ViewHolderCurrencySelected(View itemView) {
        super(itemView);
        button = (ToggleButton) itemView.findViewById(R.id.tgl_btn_currency_selected);
    }
}
