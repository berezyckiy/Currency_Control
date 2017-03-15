package maks.dev.diplom.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
