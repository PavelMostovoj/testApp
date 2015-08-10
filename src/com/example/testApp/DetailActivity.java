package com.example.testApp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity{

    final static String LOG_TAG = "Detail Activity";

    TextView idTV;
    TextView fnTV;
    TextView lnTV;
    TextView phoneTV;
    TextView emailTV;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String id = getIntent().getStringExtra("ID");

        idTV = (TextView) findViewById(R.id.DetailActivity_id);
        fnTV = (TextView) findViewById(R.id.DetailActivity_firstname);
        lnTV = (TextView) findViewById(R.id.DetailActivity_lastmane);
        phoneTV = (TextView) findViewById(R.id.DetailActivity_phone);
        emailTV = (TextView) findViewById(R.id.DetailActivity_email);

        idTV.setText(id);
        fnTV.setText("test");
        lnTV.setText("test");
        phoneTV.setText("test");
        emailTV.setText("test");

        Cursor cursor = MyActivity.dbHelper.getRec(id);

        Log.d(LOG_TAG, " cursor.getCount() = " + cursor.getCount());

        if (cursor.moveToFirst()) {

            int idColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
            int fnameColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_FIRST_NAME);
            int lnameColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_LAST_NAME);
            int phoneColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_PHONE);
            int emailColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_EMAIL);

            fnTV.setText(cursor.getString(fnameColIndex));
            lnTV.setText(cursor.getString(lnameColIndex));
            phoneTV.setText(cursor.getString(phoneColIndex));
            emailTV.setText(cursor.getString(emailColIndex));
            /**/
        } else {

        }

    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе

    }



}
