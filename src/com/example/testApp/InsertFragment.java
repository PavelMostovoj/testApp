package com.example.testApp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InsertFragment extends Fragment {

    final static String LOG_TAG = "Insert Fragment";

    EditText firstNameEt;
    EditText lastNameEt;
    EditText phoneEt;
    EditText emailEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_insert, container, false);

        firstNameEt = (EditText)rootView.findViewById(R.id.ET_firstName);
        lastNameEt = (EditText)rootView.findViewById(R.id.ET_lastName);
        phoneEt = (EditText)rootView.findViewById(R.id.ET_phone);
        emailEt = (EditText)rootView.findViewById(R.id.ET_email);


        Button readbtn = (Button)rootView.findViewById(R.id.read);
        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = MyActivity.dbHelper.getAllData();

                if (c.moveToFirst()) {

                    int idColIndex = c.getColumnIndex(SQLiteHelper.COLUMN_ID);
                    int fnameColIndex = c.getColumnIndex(SQLiteHelper.COLUMN_FIRST_NAME);
                    int lnameColIndex = c.getColumnIndex(SQLiteHelper.COLUMN_LAST_NAME);
                    int phoneColIndex = c.getColumnIndex(SQLiteHelper.COLUMN_PHONE);
                    int emailColIndex = c.getColumnIndex(SQLiteHelper.COLUMN_EMAIL);

                    do {
                        Log.d(LOG_TAG,
                                "COLUMN_ID = " + c.getInt(idColIndex) +
                                        ", first_name = " + c.getString(fnameColIndex) +
                                        ", first_name = " + c.getString(lnameColIndex) +
                                        ", phone = " + c.getString(phoneColIndex) +
                                        ", email = " + c.getString(emailColIndex));

                    } while (c.moveToNext());

                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                c.close();
            }
        });

        Button insertbtn = (Button) rootView.findViewById(R.id.insert);
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allRowstrue = true;
                int phoneint = 0;

                String firstName = firstNameEt.getText().toString();
                String lastName = lastNameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String email = emailEt.getText().toString();

                if( firstName.length() == 0 ) {
                    allRowstrue = false;
                    Toast.makeText(getActivity(), " ВВедите firstName", Toast.LENGTH_SHORT).show();
                }
                if( lastName.length() == 0 ) {
                    allRowstrue = false;
                    Toast.makeText(getActivity(), " ВВедите lastName", Toast.LENGTH_SHORT).show();
                }
                if( phone.length() == 0 ){
                    allRowstrue = false;
                    Toast.makeText(getActivity()," ВВедите phone",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        phoneint = Integer.parseInt(phone);
                    } catch (NumberFormatException e){
                        allRowstrue = false;
                        Toast.makeText(getActivity(),"поле phone дожно содержать только цифры",Toast.LENGTH_SHORT).show();
                    }
                }

                if( email.length() == 0 ) {
                    allRowstrue = false;
                    Toast.makeText(getActivity(), " ВВедите email", Toast.LENGTH_SHORT).show();
                }

                if (allRowstrue){
                    MyActivity.dbHelper.addRec(firstName, lastName, phoneint, email);
                    Toast.makeText(getActivity(),"Запись успешно вставлена ",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
                }


            }
        });

        return rootView;
    }

}
