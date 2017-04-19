package maks.dev.diplom.data.network;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.data.db.DB;
import maks.dev.diplom.interfaces.CurrencyDataListener;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/16/17.
 */

public class CurrencyData
        extends AsyncTask<Void, Void, Boolean> {

    private Activity mActivity;
    private CurrencyDataListener mListener;
    private List<Map<String, Object>> currencyList;
    private DB db;

    public CurrencyData(@NonNull Activity activity, @NonNull CurrencyDataListener listener) {
        mActivity = activity;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = new DB(mActivity);
        db.open();
        currencyList = new ArrayList<>();
        currencyList = db.getCurrenciesList();
        mListener.showProgressDialog();
    }

    private String getSavedChecked(String name) {
        for (Map<String, Object> currency : currencyList) {
            if (currency.get("name").equals(name)) {
                return currency.get("isChecked").toString();
            }
        }
        return "true";
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("http://api.fixer.io/latest?base=USD");
            String jsonStrFloatRates = sh.makeServiceCall("http://www.floatrates.com/daily/usd.json");
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject objRates = jsonObj.getJSONObject("rates");
            String date = jsonObj.getString("date");
            PreferenceUtils.saveString(mActivity, "date", date);
            JSONObject jsonObject = new JSONObject(jsonStrFloatRates);
            JSONObject jsonObjectBYN = jsonObject.getJSONObject("byn");
            JSONObject jsonObjectKZT = jsonObject.getJSONObject("kzt");
            JSONObject jsonObjectEGP = jsonObject.getJSONObject("egp");
            db.delAllData();
            db.addRec(jsonObj.getString("base"), "1", PreferenceUtils.getFullNameOfCurrency(jsonObj.getString("base")),
                    getSavedChecked(jsonObj.getString("base")));
            db.addRec(jsonObjectBYN.getString("code"), jsonObjectBYN.getString("rate"),
                    PreferenceUtils.getFullNameOfCurrency("BYN"), getSavedChecked(jsonObjectBYN.getString("code")));
            db.addRec(jsonObjectKZT.getString("code"), jsonObjectKZT.getString("rate"),
                    PreferenceUtils.getFullNameOfCurrency("KZT"), getSavedChecked(jsonObjectKZT.getString("code")));
            db.addRec(jsonObjectEGP.getString("code"), jsonObjectEGP.getString("rate"),
                    PreferenceUtils.getFullNameOfCurrency("EGP"), getSavedChecked(jsonObjectEGP.getString("code")));
            Iterator<String> iterator = objRates.keys();
            while (iterator.hasNext()) {
                String currencyName = iterator.next();
                String currencyRate = objRates.getString(currencyName);
                db.addRec(currencyName, currencyRate, PreferenceUtils.getFullNameOfCurrency(currencyName), getSavedChecked(currencyName));
            }
            db.close();
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
        mListener.hideProgressDialog();
    }

}