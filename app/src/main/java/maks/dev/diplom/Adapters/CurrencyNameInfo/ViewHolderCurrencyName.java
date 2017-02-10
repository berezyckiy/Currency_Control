package maks.dev.diplom.Adapters.CurrencyNameInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class ViewHolderCurrencyName extends RecyclerView.ViewHolder {
    TextView tvSoloCurrencyName;

    public ViewHolderCurrencyName(View itemView) {
        super(itemView);
        tvSoloCurrencyName = (TextView) itemView.findViewById(R.id.tvCurrencyOnlyName);
    }
}
