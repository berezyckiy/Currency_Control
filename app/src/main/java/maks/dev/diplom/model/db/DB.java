package maks.dev.diplom.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class DB {

    private final Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public DB(Context context) {
        mContext = context;
    }

    public void open() {
        mDBHelper = new DBHelper(mContext, "myDB", null, 1);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) {
            mDBHelper.close();
        }
    }

    public void addRec(String name, String value, String fullName, String isChecked) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("value", value);
        cv.put("fullName", fullName);
        cv.put("isChecked", isChecked);
        mSQLiteDatabase.insert("currency", null, cv);
    }

    public void updateRec(ContentValues contentValues, Integer id) {
        mSQLiteDatabase.update("currency", contentValues, "id=" + id, null);
    }

    public Cursor getAllData() {
        return mSQLiteDatabase.query("currency", null, null, null, null, null, null);
    }

    public List<Map<String, Object>> getCurrenciesList() {
        List<Map<String, Object>> currenciesList = new ArrayList<>();
        Map<String, Object> currencyMap;
        Cursor cursor = mSQLiteDatabase.query("currency", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                currencyMap = new HashMap<>();
                currencyMap.put("name", cursor.getString(cursor.getColumnIndex("name")));
                currencyMap.put("value", cursor.getString(cursor.getColumnIndex("value")));
                currencyMap.put("fullName", cursor.getString(cursor.getColumnIndex("fullName")));
                currencyMap.put("isChecked", cursor.getString(cursor.getColumnIndex("isChecked")));
                currenciesList.add(currencyMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sortCurrencyListByName(currenciesList);
    }

    private List<Map<String, Object>> sortCurrencyListByName(List<Map<String, Object>> currencyList) {
        Collections.sort(currencyList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String firstName = o1.get("name").toString();
                String secondName = o2.get("name").toString();
                return firstName.compareTo(secondName);
            }
        });
        return currencyList;
    }

    public void delRec(long id) {
        mSQLiteDatabase.delete("currency", "id = " + id, null);
    }

    public void delAllData() {
        mSQLiteDatabase.delete("currency", null, null);
    }

    public boolean isData() {
        return (mSQLiteDatabase.query("currency", null, null, null, null, null, null).moveToFirst());
    }
}
