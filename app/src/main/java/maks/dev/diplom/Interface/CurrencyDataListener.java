package maks.dev.diplom.Interface;

/**
 * Created by berezyckiy on 2/17/17.
 */

public interface CurrencyDataListener {
    void onSuccessLoadingData();

    void onErrorLoadingData();

    void showProgressBar();

    void hideProgressBar();
}
