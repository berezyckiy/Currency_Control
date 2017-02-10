package maks.dev.diplom.Adapters.CurrencySelected;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class ViewHolderCurrencySelected extends RecyclerView.ViewHolder {
    TextView tvCurrencySelectedName;
    CheckBox cbCurrencySelected;

    public ViewHolderCurrencySelected(View itemView) {
        super(itemView);
        tvCurrencySelectedName = (TextView) itemView.findViewById(R.id.tvCurrencySelectedName);
        cbCurrencySelected = (CheckBox) itemView.findViewById(R.id.cbCurrencySelected);
    }
}
