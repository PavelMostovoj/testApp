package com.example.testApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper {

    static final String LOG_TAG = "SQLite Helper";

    private final Context mCtx;

    static final String DB_NAME = "myDBSqlite";
    static final int    DB_VERSION = 1;
    static final String DB_TABLE_NAME = "mytable";

    static final String COLUMN_ID = "id";
    static final String COLUMN_FIRST_NAME = "first_name";
    static final String COLUMN_LAST_NAME = "last_name";
    static final String COLUMN_PHONE = "phone";
    static final String COLUMN_EMAIL = "email";

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    boolean open = false;

    public SQLiteHelper(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        open = true;
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE_NAME, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String firstName, String lastName, int phone, String email) {

        ContentValues cv = new ContentValues();

        cv.put(SQLiteHelper.COLUMN_FIRST_NAME, firstName);
        cv.put(SQLiteHelper.COLUMN_LAST_NAME, lastName);
        cv.put(SQLiteHelper.COLUMN_PHONE, phone);
        cv.put(SQLiteHelper.COLUMN_EMAIL, email);

        long rowID = mDB.insert(SQLiteHelper.DB_TABLE_NAME, null, cv);
        Log.d(LOG_TAG, "row inserted, COLUMN_ID = " + rowID);

    }

    // удалить запись из DB_TABLE_NAME
    public void delRec(long id) {
        mDB.delete(DB_TABLE_NAME, COLUMN_ID + " = " + id, null);
    }



    public Cursor getRec(String id)
    {
        return mDB.rawQuery("Select*from " + DB_TABLE_NAME + " where id=" + id, null);
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("", "--- onCreate database ---");

            db.execSQL("create table " + DB_TABLE_NAME + " ("
                    + COLUMN_ID + " integer primary key autoincrement,"
                    + COLUMN_FIRST_NAME + " text,"
                    + COLUMN_LAST_NAME + " text,"
                    + COLUMN_PHONE + " integer,"
                    + COLUMN_EMAIL + " text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

}
