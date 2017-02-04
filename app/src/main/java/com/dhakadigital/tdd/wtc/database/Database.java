package com.dhakadigital.tdd.wtc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.dhakadigital.tdd.wtc.pojo.OrgInfo;

import java.util.ArrayList;

/**
 * Created by y34h1a on 2/4/17.
 */

public class Database  {
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mHelper;

    public Database(Context context){
        mHelper = new DatabaseHelper(context.getApplicationContext());
        mDatabase = mHelper.getReadableDatabase();
    }

    private String[] org_info_column = {
            DatabaseHelper.C_UID,
            DatabaseHelper.C_ORG_NAME,
            DatabaseHelper.C_ORG_ADDRESS
    };

    public void insertOrgInfo(OrgInfo orgInfo){
        String sql = "INSERT INTO " + (DatabaseHelper.TABLE_ORANIZATION_INFO + " VALUES(?,?,?)");
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        statement.bindString(2,orgInfo.getName());
        statement.bindString(3,orgInfo.getAddress());
        statement.execute();

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<OrgInfo> getAllOrgInfo(){
        ArrayList<OrgInfo> listOrgInfo = new ArrayList<>();
        Cursor cursor;
        cursor = mDatabase.query(DatabaseHelper.TABLE_ORANIZATION_INFO,org_info_column,
                null,null,null,null,DatabaseHelper.C_UID+" DESC");

        if(cursor != null && cursor.moveToFirst()){
            do{
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_ORG_NAME)));
                orgInfo.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_ORG_ADDRESS)));
                listOrgInfo.add(orgInfo);

            }while (cursor.moveToNext());
        }

        return listOrgInfo;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "wtc_db";
        private static final int DB_VERSION = 1;
        private Context mContext;

        //TABLES
        public static final String TABLE_ORANIZATION_INFO  = "org_info";
        public static final String TABLE_WAGE = "table_wage";

        //ORGANIZATION COLUMS
        public static final String C_UID = "id";
        public static final String C_ORG_NAME = "org_name";
        public static final String C_ORG_ADDRESS = "org_address";


        private static final String CREATE_TABLE_ORG_INFO = "CREATE TABLE " + TABLE_ORANIZATION_INFO + " (" +
                C_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_ORG_NAME + " TEXT," +
                C_ORG_ADDRESS + " TEXT" +
                ");";


        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_TABLE_ORG_INFO);
            }catch (SQLiteException exception){
                exception.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{

                db.execSQL(" DROP TABLE " + CREATE_TABLE_ORG_INFO + " IF EXISTS;");
                onCreate(db);
            }catch (SQLiteException exception){
                exception.printStackTrace();
            }
        }
    }
}
