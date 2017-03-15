package maks.dev.diplom.Adapters.CurrencyNameInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

class ViewHolderCurrencyName
        extends RecyclerView.ViewHolder {

    ImageView imgViewRecyclerView;
    TextView tvSoloCurrencyName;

    ViewHolderCurrencyName(View itemView) {
        super(itemView);
        imgViewRecyclerView = (ImageView) itemView.findViewById(R.id.imgViewRecyclerView);
        tvSoloCurrencyName = (TextView) itemView.findViewById(R.id.tvCurrencyOnlyName);
    }
}
