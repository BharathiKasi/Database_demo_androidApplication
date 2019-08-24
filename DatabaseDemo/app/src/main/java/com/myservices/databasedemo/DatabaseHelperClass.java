package com.myservices.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.sql.Types.INTEGER;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    DatabaseTableClass databaseTableClass;
    SQLiteDatabase sqLiteDatabase_Test = this.getWritableDatabase();
    public static final String DATABASE_NAME = "student.db";
    private static final String TAG="DatabaseHelperrCass";


    public DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 3);
        Log.i(TAG,"this is the databaseHelper class constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"this is the oncreate method");

        databaseTableClass = databaseObjectChecker();
        databaseTableClass.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion , int newVersion) {
        /*
        //this is method is called when we want to update our existing table like adding the new column in to the table
        //so that we will delete the existing table by using the drop table command and then we call the new table to existing database
         */
        if(olderVersion<newVersion){
            Log.i(TAG,"this is onUpgrade method ");
            databaseTableClass=databaseObjectChecker();
            databaseTableClass.onUpgradeTable(sqLiteDatabase,newVersion);

        }


        Log.i(TAG,"this is the onUpgrade method for the sqlite database");

        //databaseTableClass = databaseObjectChecker();
        //databaseTableClass.dropTable(sqLiteDatabase);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(sqLiteDatabase);
    }

    public boolean insertData(String userID_unique, String name, String surname, String marks) {


        /*
        //this method is used to insert the data to student table
        // SQLiteDatabase sqLiteDatabase=this.getWritableDatabase(); is used to call the  sqlite oncreate method
        //then contentValues is used to insert the user data like map key value pair
        // the insert method is used to insert the data to student table
        */


        //SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        databaseTableClass = databaseObjectChecker();
        long result = databaseTableClass.insetData(sqLiteDatabase_Test, userID_unique, name, surname, marks);
        if (result == -1) {
            return false;
        } else {
            return true;
        }


    }

    public Cursor getAllData() {


        //SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        databaseTableClass = databaseObjectChecker();
        Cursor cursor = databaseTableClass.getAllData(sqLiteDatabase_Test);
        //Cursor cursor=sqLiteDatabase.rawQuery("select * from " +TABLE_NAME,null);
        return cursor;
    }

    public boolean updateData(String userId_unique, String username, String user_surname, String mark) {

        databaseTableClass = databaseObjectChecker();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = databaseTableClass.modifyUserData(sqLiteDatabase, userId_unique, username, user_surname, mark);

        if (result == 1) {
            return true;
        }
        return false;
    }

    public boolean deleteData(String user_id) {

        //SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        databaseTableClass = databaseObjectChecker();
        int result = databaseTableClass.deleteUserData(sqLiteDatabase_Test, user_id);

        if (result == 1) {
            return true;
        } else {
            return false;
        }


    }

    public DatabaseTableClass databaseObjectChecker() {
        if (databaseTableClass == null) {
            databaseTableClass = new DatabaseTableClass();

        }
        return databaseTableClass;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i(TAG,"this is the onopen method ");

    }
}
