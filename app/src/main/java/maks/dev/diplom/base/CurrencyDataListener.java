package maks.dev.diplom.base;

/**
 * Created by berezyckiy on 2/17/17.
 */

public interface CurrencyDataListener {
    void onSuccessLoadingData();

    void onErrorLoadingData();

    void showProgressDialog();

    void hideProgressDialog();
}
