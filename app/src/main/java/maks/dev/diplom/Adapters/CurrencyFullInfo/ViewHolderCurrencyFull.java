package maks.dev.diplom.Adapters.CurrencyFullInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class ViewHolderCurrencyFull
        extends RecyclerView.ViewHolder {

    ImageView imgViewRecyclerView;
    TextView currencyName;
    TextView currencyValue;
    TextView currencyFullName;

    public ViewHolderCurrencyFull(View itemView) {
        super(itemView);
        imgViewRecyclerView = (ImageView) itemView.findViewById(R.id.imgViewRecyclerView);
        currencyName = (TextView) itemView.findViewById(R.id.tvCurrencyName);
        currencyValue = (TextView) itemView.findViewById(R.id.tvCurrencyValue);
        currencyFullName = (TextView) itemView.findViewById(R.id.tvCurrencyFullName);
    }
}
