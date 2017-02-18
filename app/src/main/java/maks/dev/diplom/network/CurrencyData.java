package maks.dev.diplom.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.Iterator;

import maks.dev.diplom.Data.DB;
import maks.dev.diplom.Interface.CurrencyDataListener;

/**
 * Created by berezyckiy on 2/16/17.
 */

public class CurrencyData extends AsyncTask<Void, Void, Boolean> {

    private Activity mActivity;
    private CurrencyDataListener mListener;

    public CurrencyData(@NonNull Activity activity, @NonNull CurrencyDataListener listener) {
        mActivity = activity;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.showProgressBar();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("http://api.fixer.io/latest?base=USD");
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject objRates = jsonObj.getJSONObject("rates");
            DB db = new DB(mActivity);
            db.open();
            db.delAllData();
            db.addRec(jsonObj.getString("base"), "1", jsonObj.getString("base"), "true");
            Iterator<String> iterator = objRates.keys();
            while (iterator.hasNext()) {
                String nameCurrency = iterator.next();
                String currencyRate = objRates.getString(nameCurrency);
                db.addRec(nameCurrency, currencyRate, nameCurrency, "true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        if (isSuccess) {
            mListener.onSuccessLoadingData();
        } else {
            mListener.onErrorLoadingData();
        }
        mListener.hideProgressBar();
    }

}