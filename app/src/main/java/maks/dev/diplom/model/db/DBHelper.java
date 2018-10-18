package maks.dev.diplom.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berezyckiy on 2/10/17.
 */

public class DBHelper
        extends SQLiteOpenHelper {

    private final String DB_CREATE = "create table currency(id integer primary key autoincrement," +
            "name text, value real, fullName text, isChecked text);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
