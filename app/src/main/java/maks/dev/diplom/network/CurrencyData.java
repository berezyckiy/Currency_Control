package maks.dev.diplom.network;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Data.DB;
import maks.dev.diplom.Interface.CurrencyDataListener;

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
        currencyList = new ArrayList<>();
        db.open();
        checkExistDataDB();
        mListener.showProgressBar();
    }

    private void checkExistDataDB() {
        Cursor cursor = db.getAllData();
        Map<String, Object> tmpMap;
        if (cursor.moveToFirst()) {
            do {
                tmpMap = new HashMap<>();
                String isChecked = cursor.getString(cursor.getColumnIndex("isChecked"));
                tmpMap.put("isChecked", isChecked);
                currencyList.add(tmpMap);
            } while (cursor.moveToNext());
        }
    }

    private String getSavedChecked(int id) {
        if (currencyList.size() != 0) {
            return currencyList.get(id).get("isChecked").toString();
        } else {
            return "true";
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("http://api.fixer.io/latest?base=USD");
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject objRates = jsonObj.getJSONObject("rates");
            db.delAllData();
            int i = 0;
            db.addRec(jsonObj.getString("base"), "1", jsonObj.getString("base"), getSavedChecked(i));
            i++;
            db.addRec("BYN", "1.8751", "BYN", getSavedChecked(i));
            Iterator<String> iterator = objRates.keys();
            while (iterator.hasNext()) {
                String nameCurrency = iterator.next();
                String currencyRate = objRates.getString(nameCurrency);
                db.addRec(nameCurrency, currencyRate, nameCurrency, getSavedChecked(++i));
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
        mListener.hideProgressBar();
    }

}