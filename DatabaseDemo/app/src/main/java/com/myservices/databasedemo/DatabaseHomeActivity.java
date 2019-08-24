package com.myservices.databasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHomeActivity extends AppCompatActivity {
    EditText edit_text_name, edit_text_surname, edit_text_marks, userId_edittext;
    Button insert_button, showButon, deleteButton, updateButton;
    String name, surname, marks;
    String userId, userName, userSurname, userMarks, userId_unique;
    DataHelperClass dataHelperClass;
    List duplicateList;
    int mark;
    DatabaseHelperClass databaseHelperClass;
    List list = new ArrayList();
    private static int SHOWACTIVITY_REQUESTCODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());
        edit_text_name               = findViewById(R.id.edit_text_name);
        edit_text_surname            = findViewById(R.id.edit_text_surname);
        edit_text_marks              = findViewById(R.id.edit_text_marks);
        userId_edittext              = findViewById(R.id.id_edit_text);
        insert_button                = findViewById(R.id.insert_button);
        showButon                    = findViewById(R.id.show_button);
        deleteButton                 = findViewById(R.id.delete);
        updateButton                 = findViewById(R.id.modify);


        databaseHelperClass = new DatabaseHelperClass(this);

    }

    @Override
    protected void onResume() {

        super.onResume();

        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDataFromUser();
                if (name.equals("") && userId_unique.equals("")) {
                    Toast.makeText(DatabaseHomeActivity.this, " your name  and  userId is mandatory", Toast.LENGTH_LONG).show();
                } else {
                    boolean result = databaseHelperClass.insertData(userId_unique, name, surname, marks);
                    if (result == true) {
                        Toast.makeText(DatabaseHomeActivity.this, "data is inserted", Toast.LENGTH_LONG).show();

                        clearData();
                    } else {
                        Toast.makeText(DatabaseHomeActivity.this, "your data is not inserted please check the user id", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        showButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelperClass.getAllData();
                list = getDataFromDatabase(cursor);

                for (Object string : list) {
                    Log.i("DATABASEHOMEACTIVITY", string.toString() + "\n");


                }
                Intent intent = new Intent(DatabaseHomeActivity.this, ShowDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) list);
                intent.putExtras(bundle);
                startActivityForResult(intent, SHOWACTIVITY_REQUESTCODE);


            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromUser();
                if (userId_unique.equals("")) {
                    Toast.makeText(DatabaseHomeActivity.this, "userID is mandatory", Toast.LENGTH_LONG).show();
                } else {
                    boolean result = databaseHelperClass.updateData(userId_unique, name, surname, marks);

                    if (result) {
                        Toast.makeText(DatabaseHomeActivity.this, "you data is modified ", Toast.LENGTH_LONG).show();
                        Cursor cursor = databaseHelperClass.getAllData();
                        list = getDataFromDatabase(cursor);
                        clearData();


                    } else {
                        Toast.makeText(DatabaseHomeActivity.this, "your data is not modified please check the user id ", Toast.LENGTH_LONG).show();
                        clearData();
                    }


                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromUser();
                if (userId_unique.equals("")) {
                    Toast.makeText(DatabaseHomeActivity.this, "your id is mandatory to delete your data from the database", Toast.LENGTH_LONG).show();
                } else {
                    boolean result = databaseHelperClass.deleteData(userId_unique);
                    if (result) {
                        Cursor cursor = databaseHelperClass.getAllData();
                        getDataFromDatabase(cursor);
                        clearData();
                    }
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOWACTIVITY_REQUESTCODE && resultCode == RESULT_OK) {

            ContentResolver contentResolver=getContentResolver();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //list=null;
    }

    public List getDataFromDatabase(Cursor cursor) {

        DataHelperClass dataHelperClass123;
        int count = 0;
        if (list.size() > 0) {
            duplicateList = list;

            list.removeAll(duplicateList);
        }


        while (cursor.moveToNext()) {
            count++;
            dataHelperClass = new DataHelperClass();
            userId = cursor.getString(0);
            userName = cursor.getString(1);
            userSurname = cursor.getString(2);
            userMarks = cursor.getString(3);
            for (Object object : list) {

                dataHelperClass123 = (DataHelperClass) object;
                if (dataHelperClass123.getId() == userId) {
                    count = 0;
                    break;
                }
            }
            if (count > 0) {
                dataHelperClass.setId(userId);
                dataHelperClass.setName(userName);
                dataHelperClass.setSurname(userSurname);
                dataHelperClass.setMarks(userMarks);
                list.add(dataHelperClass);
            }


        }
        return list;
    }

    public void clearData() {

        edit_text_marks.setText("");
        edit_text_name.setText("");
        edit_text_surname.setText("");
        userId_edittext.setText("");
    }

    public void getDataFromUser() {
        name = edit_text_name.getText().toString().trim();
        userId_unique = userId_edittext.getText().toString().trim();
        surname = edit_text_surname.getText().toString().trim();
        marks = edit_text_marks.getText().toString().trim();


    }

}
