package com.myservices.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static org.mozilla.javascript.Token.AND;

public class DatabaseTableClass {
    private static String TAG="DatabaseTableClass";
    public static  String TABLE_NAME = "student_table";
    public static String userUniqueId = "USER_ID_UNIQUE";
    public static String userName = "NAME";
    public static String user_Surname = "SURNAME";
    public static String user_Marks = "MARKS";
    public static  String User_Interested123= "INTERESTED1234";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + userUniqueId + " TEXT PRIMARY KEY,"
            + userName + "  TEXT," + user_Surname + " TEXT," + user_Marks + " INTEGER" + " )";
    private final String CREATE_TABLE2="ALTER TABLE "+ TABLE_NAME +" ADD "+ User_Interested123 +" TEXT ";
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"this is the oncreate method for the create table");

        /*

        //this will be called when the DatabaseHelper  call oncerate method is called .
        //this method is used to create the table  by using the  CREATE_TABLE command  string.

         */


        sqLiteDatabase.execSQL(CREATE_TABLE);


        }




        public void onUpgradeTable(SQLiteDatabase sqLiteDatabase,int version){

        switch (version){
            case 3:
                Log.i(TAG,"alter table");
                sqLiteDatabase.execSQL("ALTER TABLE "+TABLE_NAME.trim()+" ADD COLUMN "+ User_Interested123.trim() +"String ");
        }


        }

    public void dropTable(SQLiteDatabase sqLiteDatabase) {
        /*

        //this method will be called when the DatabaseHelper onUpgrade() method is called .
        // this method is used to delete the table from the databbase.

         */


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long insetData(SQLiteDatabase sqLiteDatabase, String userID_unique, String name, String surname, String marks) {


        /*

        //this is will be called when the DatabseHelper insertData method is called .
        // this method is used to insert the user data to the current table .
        //insert method required three parameter
        //1. TABLE_NAME this should be the first argument .
        //2. nullColumnHack this is used when we insert to empty row in the current table mostly will pass the null here .
        //3. the the last is contentValues object which is used to hold the user data like key value pair .
        //insert method will return the long value if suppose it's return -1 then our data is not inserted into current table so we have to intimate to the user.

         */


        ContentValues contentValues = new ContentValues();
        contentValues.put(userUniqueId, userID_unique);
        contentValues.put(userName, name);
        contentValues.put(user_Surname, surname);
        contentValues.put(user_Marks, marks);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result;
    }

    public Cursor getAllData(SQLiteDatabase sqLiteDatabase) {


        /*

        //this method is invoked when the DatabaeHelper class  getAllData method  run .
        // this is return the cursor object which is travel around the our table
        // cursor.moveToFirst() method is used to move to first row of the current table.
        // similarly cursro.moveToLast method is used to move to the last row of the current table.
        // sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null); this method is used to get the all the user data from the current table
        // this first argument is our query  and the second argument is TABLE_NAME  and the last argument is selection options it is string array if you want to select the particiular user data using this third arginement
        // to mention the user data inorder to get the particular user data .
        // if suppose you want to get the all the user data then pass the null as a third argument .

           */

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursor;
    }

    public int deleteUserData(SQLiteDatabase sqLiteDatabase, String user_id) {


        /*

        // this method is invoked when the DatabaseHelper class  deleteData method is executed .
        // this method is used to delet the user data based on the user ID
        // sqLiteDatabase.delete(TABLE_NAME, "USER_ID_UNIQUE= ?", new String[]{user_id}); will delete the particular user  from the table
        // it has 3 argument .
        // 1. TABLE_NAME it should be the first argumant and it not null.
        // 2. then whereClause where we can specfy the table column names
        // we can add as much as possble  by using the AND command
        // (EG)--> sqLiteDatabase.delete(TABLE_NAME, userUniqueId+" AND "+ userName +" AND "+user_Surname+" AND"+ user_Marks+"=? " , new String[]{user_id,name, surname, marks});
        //if it's   return 1 then user data is deleted else the uesr data is not matched with specifed data inside the currnet tabble.

         */

        int result = sqLiteDatabase.delete(TABLE_NAME, "USER_ID_UNIQUE= ?", new String[]{user_id});

        return result;

    }

    public int modifyUserData(SQLiteDatabase sqLiteDatabase, String userId_unique, String username, String user_surname, String mark) {


        /*

        // this method is invoked when the DatabaseHelper class  updata method is executed .
        // this metod is used to update the user existing data with specified data .
        // sqLiteDatabase.update(TABLE_NAME, contentValues, userUniqueId + "= ?", new String[]{userId_unique}); will execute the update command
        // it has four parmaneter .
        //1. TABLE_NAME  it should be the first argument not null.
        //2. then the second argument contentvalue object it have the updated valu of the user .
        //3. whereClause what are the column user want to update we will specify those column in the theid argument .
        //4. the last argument is string array[] here we will specify the user enter data.

         */

        int result = 0;
        ContentValues contentValues = new ContentValues();
        if (!(userId_unique.equals(""))) {
            // SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
            contentValues.put(userUniqueId, userId_unique);
            if (!(username.equals(""))) {
                contentValues.put(userName, username);
            }
            if (!(user_surname.equals(""))) {
                contentValues.put(user_Surname, user_surname);
            }
            if (!(mark.equals(""))) {
                contentValues.put(user_Marks, mark);
            }


            /*
            // the first one used to update the data based on the unique if it's match with curret table then it will modify the user data
            // the socond one is used to update the user data based on all the details if all the datails is matched then it will update the user data to current table
            // the third one is used to update the used data based on any one details is satisfied if any used given details is matched with table then the current table data will modified
             */


            //result = sqLiteDatabase.update(TABLE_NAME, contentValues, userUniqueId + "= ?", new String[]{userId_unique});
           // sqLiteDatabase.update(TABLE_NAME, contentValues, userUniqueId +"= ?"+" AND "+ userName+"= ?"+" AND "+user_Surname+"= ?"+" AND "+user_Marks+"= ?", new String[]{userId_unique,username,user_surname,mark});
            result=sqLiteDatabase.update(TABLE_NAME, contentValues, userUniqueId +"= ?"+" OR "+ userName+"= ?"+" OR "+user_Surname+"= ?"+" OR "+user_Marks+"= ?", new String[]{userId_unique,username,user_surname,mark});

        }

        return result;

    }
}
