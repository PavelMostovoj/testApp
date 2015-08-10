package com.example.testApp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SelectFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    final static String LOG_TAG = "Select Fragment";

    ListView List;
    private ListAdapter mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        mListAdapter = new ListAdapter(getActivity());
        List = (ListView)rootView.findViewById(R.id.List);
        List.setAdapter(mListAdapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent = new Intent(parent.getContext(), DetailActivity.class);

                int idfromDB = (Integer)view.findViewById(R.id.TV_id).getTag();

                intent.putExtra("ID", String.valueOf(idfromDB));
                startActivity(intent);
            }
        });

        // создаем лоадер для чтения данных
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return rootView;
    }


    public void onResume() {
        super.onResume();

        Log.d(LOG_TAG, " onResume");
        getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
    }

    public void onPause() {
        super.onPause();

        Log.d(LOG_TAG, " onPause");
    }


    private class ListAdapter extends BaseAdapter {

        Cursor cursor;

        private LayoutInflater mInflator;
        Context ctx;

        public ListAdapter(Context context) {
            super();
            ctx = context;
            mInflator = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void swapCursor(Cursor cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            if (cursor == null) return 0;
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            //return tableRow.get(i);
            if (cursor.moveToPosition(position)) {

                int idColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
                int fnameColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_FIRST_NAME);
                int lnameColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_LAST_NAME);
                int phoneColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_PHONE);
                int emailColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_EMAIL);

                return new PersonEntry(cursor.getInt(idColIndex),
                        cursor.getString(fnameColIndex), cursor.getString(lnameColIndex),
                        cursor.getInt(phoneColIndex),cursor.getString(emailColIndex));

            } else {
                return null;
            }

        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public long getItemIdFromDB(int position) {
            if(cursor.moveToPosition(position)){
                int idColIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
                return cursor.getInt(idColIndex);
            }

            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (view == null) {
                view = mInflator.inflate(R.layout.list_listitem, null);

                viewHolder = new ViewHolder();
                viewHolder.idTv = (TextView) view.findViewById(R.id.TV_id);
                viewHolder.firstnameTv = (TextView) view.findViewById(R.id.TV_firstName);
                viewHolder.lastnameTv = (TextView) view.findViewById(R.id.TV_last_name);
                viewHolder.phoneTv = (TextView) view.findViewById(R.id.TV_phone);
                viewHolder.emailTv = (TextView) view.findViewById(R.id.TV_email);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            PersonEntry tempPersonEntry = (PersonEntry)getItem(i);
            if(tempPersonEntry != null)
            {
                viewHolder.idTv.setText(String.valueOf(tempPersonEntry.getId()));
                viewHolder.idTv.setTag(tempPersonEntry.getId());
                viewHolder.firstnameTv.setText(tempPersonEntry.getFirstName());
                viewHolder.lastnameTv.setText(tempPersonEntry.getLastName());
                viewHolder.phoneTv.setText(String.valueOf(tempPersonEntry.getPhone()));
                viewHolder.emailTv.setText(tempPersonEntry.getEmail());
            }


            return view;
        }
    }


    static class ViewHolder {
        TextView idTv;
        TextView firstnameTv;
        TextView lastnameTv;
        TextView phoneTv;
        TextView emailTv;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), MyActivity.dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    static class MyCursorLoader extends CursorLoader {

        SQLiteHelper db;

        public MyCursorLoader(Context context, SQLiteHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();

            return cursor;
        }
    }



}

