package com.dhakadigital.tdd.wtc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.dhakadigital.tdd.wtc.model.EarningInfo;
import com.dhakadigital.tdd.wtc.model.SheetInfo;
import com.dhakadigital.tdd.wtc.model.OrgInfo;
import com.dhakadigital.tdd.wtc.model.UserInfo;

import java.util.ArrayList;

/**
 * Created by y34h1a on 2/4/17.
 */

public class Database {
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mHelper;

    public Database(Context context) {
        mHelper = new DatabaseHelper(context.getApplicationContext());
        mDatabase = mHelper.getReadableDatabase();
    }

    //----------------------------STRING ARRAY-----------------------------------
    //organization
    private String[] org_info_column = {
            DatabaseHelper.C_USER_UID,
            DatabaseHelper.C_ORG_NAME,
            DatabaseHelper.C_ORG_ADDRESS
    };

    //SHEET INFO
    private String[] sheet_info_column = {
            DatabaseHelper.C_S_UID,
            DatabaseHelper.C_S_ORG_UID,
            DatabaseHelper.C_S_USER_UID,
            DatabaseHelper.C_S_NAME,
            DatabaseHelper.C_S_ORG_NAME,
            DatabaseHelper.C_S_ORG_ADDRESS,
            DatabaseHelper.C_S_HOUR_RATE
    };

    //USER EARNING
    private String[] user_earning_info_column = {
            DatabaseHelper.C_E_UID,
            DatabaseHelper.C_E_SHEET_UID,
            DatabaseHelper.C_E_USER_UID,
            DatabaseHelper.C_E_DATE,
            DatabaseHelper.C_E_START_TIME,
            DatabaseHelper.C_E_DURATION,
            DatabaseHelper.C_E_WAGES
    };

    //USER INFO
    private String[] user_info_column = {
            DatabaseHelper.C_U_UID,
            DatabaseHelper.C_U_NAME,
            DatabaseHelper.C_U_EMAIL
    };

    //----------------------------INSERT DATA IN TABLES-----------------------------------
    //USER DATA INSERT
    public void insertUserInfo(UserInfo userInfo){
        String sql = "INSERT INTO " + (DatabaseHelper.TABLE_USER_INFO + " VALUES(?,?)");
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
    }

    //ORGANIZATION DATA INSERT
    public void insertOrgInfo(OrgInfo orgInfo) {
        String sql = "INSERT INTO " + (DatabaseHelper.TABLE_ORGANIZATION_INFO + " VALUES(?,?,?)");
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        statement.bindString(2, orgInfo.getName());
        statement.bindString(3, orgInfo.getAddress());
        statement.execute();

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    //USER INCOME DATA INSERT
    public void insertEarningInfo(EarningInfo earningInfo) {
        String sql = "INSERT INTO " + (DatabaseHelper.TABLE_WAGE + " VALUES(?,?,?,?,?,?)");
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        statement.bindString(2, earningInfo.getSheet_uid());
        statement.bindString(3, earningInfo.getDate_in_millis());
        statement.bindString(4, earningInfo.getStart_time_millis());
        statement.bindString(5, earningInfo.getDuration());
        statement.bindString(6, earningInfo.getWages());
        statement.execute();

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    //SHEET INFO DATA INSERT
    public void insertSheetInfo(SheetInfo sheetInfo) {
        String sql = "INSERT INTO " + (DatabaseHelper.TABLE_SHEET + " VALUES(?,?,?,?,?,?)");
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        statement.bindString(2, sheetInfo.getName());
        statement.bindString(3, sheetInfo.getOrg_uid());
        statement.bindString(4, sheetInfo.getOrg_name());
        statement.bindString(5, sheetInfo.getOrg_address());
        statement.bindDouble(6, sheetInfo.getHourRate());
        statement.execute();

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }


    //----------------------------GET DATA FROM TABLES-----------------------------------
    //ORGANIZATION GET ALL ORG INFO
    public ArrayList<OrgInfo> getAllOrgInfo() {
        ArrayList<OrgInfo> listOrgInfo = new ArrayList<>();
        Cursor cursor;
        cursor = mDatabase.query(DatabaseHelper.TABLE_ORGANIZATION_INFO, org_info_column,
                null, null, null, null, DatabaseHelper.C_UID + " ASC");
//        cursor = mDatabase.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_ORGANIZATION_INFO+" ORDER BY "+DatabaseHelper.C_UID+" ASC",null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.C_UID)));
                orgInfo.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_ORG_NAME)));
                orgInfo.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_ORG_ADDRESS)));
                listOrgInfo.add(orgInfo);

            } while (cursor.moveToNext());
        }

        return listOrgInfo;

    }

    //USER EARNING GET ALL INFO
    public ArrayList<EarningInfo> getAllEarningInfo() {
        ArrayList<EarningInfo> listEarningInfo = new ArrayList<>();
        Cursor cursor;
        cursor = mDatabase.query(DatabaseHelper.TABLE_WAGE, user_earning_info_column, null, null, null, null, DatabaseHelper.C_E_UID + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                EarningInfo earningInfo = new EarningInfo();
                earningInfo.setDate_in_millis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_DATE)));
                earningInfo.setStart_time_millis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_START_TIME)));
                earningInfo.setDuration(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_DURATION)));
                earningInfo.setWages(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_WAGES)));
                listEarningInfo.add(earningInfo);
            } while (cursor.moveToNext());
        }
        return listEarningInfo;
    }

    //SHEET GET ALL INFO
    public ArrayList<SheetInfo> getAllSheetInfo() {
        ArrayList<SheetInfo> listSheetInfo = new ArrayList<>();
        Cursor cursor;
        cursor = mDatabase.query(DatabaseHelper.TABLE_SHEET, sheet_info_column, null, null, null, null, DatabaseHelper.C_S_UID + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                SheetInfo sheetInfo = new SheetInfo();
                sheetInfo.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.C_S_UID)));
                sheetInfo.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_S_NAME)));
                sheetInfo.setOrg_uid(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_S_ORG_UID)));
                sheetInfo.setOrg_name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_S_ORG_NAME)));
                sheetInfo.setOrg_address(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_S_ORG_ADDRESS)));
                sheetInfo.setHourRate(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.C_S_HOUR_RATE)));
                listSheetInfo.add(sheetInfo);
            } while (cursor.moveToNext());
        }
        return listSheetInfo;
    }


    //----------------------------SEARCH QUERY FROM EARNING TABLE-----------------------------------
    //row_count = date(how many day i worked / how many entry there)

    //USER EARNING GET ALL INFO
    public ArrayList<EarningInfo> getEarningInfoBySheet(String sheetUid) {
        ArrayList<EarningInfo> listEarningInfo = new ArrayList<>();
        Cursor cursor;
//        cursor = mDatabase.query(DatabaseHelper.TABLE_WAGE, user_earning_info_column,null,null,null,null,DatabaseHelper.C_E_UID+" DESC");
        cursor = mDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_WAGE + " WHERE  sheet_uid ='" + sheetUid + "'", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                EarningInfo earningInfo = new EarningInfo();
                earningInfo.setDate_in_millis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_DATE)));
                earningInfo.setStart_time_millis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_START_TIME)));
                earningInfo.setDuration(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_DURATION)));
                earningInfo.setWages(cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_E_WAGES)));
                listEarningInfo.add(earningInfo);
            } while (cursor.moveToNext());
        }
        return listEarningInfo;
    }

    //---get total worked day from "TABLE_WAGE" where sheet id is selected from spinner--
    public int getTotalDayCount(int sheetId) {
        int totalDay;
        Cursor mCursorCount = mDatabase.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_WAGE + " WHERE sheet_uid ='" + sheetId + "'", null);
        mCursorCount.moveToFirst();
        totalDay = mCursorCount.getInt(0);
        mCursorCount.close();
        return totalDay;
    }

    //---get total of wage and duration day--
    public int getTotalWage(int sheetId) {
        int totalWage;
        Cursor mSumCursor = mDatabase.rawQuery("SELECT SUM(wages) FROM " + DatabaseHelper.TABLE_WAGE + " WHERE sheet_uid ='" + sheetId + "'", null);
        mSumCursor.moveToFirst();
        totalWage = mSumCursor.getInt(0);
        mSumCursor.close();
        return totalWage;
    }

    //---get total of duration from wage table ---
    public long getTotalDuration(int sheetId) {
        int totalDuration;
        Cursor mSumCursor = mDatabase.rawQuery("SELECT SUM(duration) FROM " + DatabaseHelper.TABLE_WAGE + " WHERE sheet_uid ='" + sheetId + "'", null);
        mSumCursor.moveToFirst();
        totalDuration = mSumCursor.getInt(0);
        mSumCursor.close();
        return totalDuration;
    }

    //----------------------------INNER DATABASE CLASS-----------------------------------
    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "wtc_db";
        private static final int DB_VERSION = 1;
        private Context mContext;

        //----------------------------TABLES-----------------------------------
        //TABLES
        private static final String TABLE_ORGANIZATION_INFO = "table_org";
        private static final String TABLE_WAGE = "table_wage";
        private static final String TABLE_SHEET = "table_sheet";
        private static final String TABLE_USER_INFO = "table_user";

        //----------------------------COLUMNS-----------------------------------
        //ORGANIZATION COLUMS
        private static final String C_UID = "id";
        private static final String C_USER_UID = "user_id";
        private static final String C_ORG_NAME = "org_name";
        private static final String C_ORG_ADDRESS = "org_address";

        //SHEET INFO COLUMNS
        private static final String C_S_UID = "id";
        private static final String C_S_ORG_UID = "org_uid";
        private static final String C_S_USER_UID = "user_id";
        private static final String C_S_NAME = "name";
        private static final String C_S_ORG_NAME = "org_name";
        private static final String C_S_ORG_ADDRESS = "org_address_sheet";
        private static final String C_S_HOUR_RATE = "org_hour_rate";

        //USER EARNING COLUMN
        // variable : e_ss_time (earning_start_stop_time);
        private static final String C_E_UID = "id";
        private static final String C_E_SHEET_UID = "sheet_uid";
        private static final String C_E_USER_UID = "user_uid";
        private static final String C_E_DATE = "e_date";
        private static final String C_E_START_TIME = "e_ss_time";
        private static final String C_E_DURATION = "duration";
        private static final String C_E_WAGES = "wages";

        //USER TABLE
        private static final String C_U_UID = "id";
        private static final String C_U_NAME = "user_name";
        private static final String C_U_EMAIL = "user_email";

        //----------------------------CREATE TABLES QUERY-----------------------------------
        //ORGANIZATION TABLE CREATE QUERY
        private static final String CREATE_TABLE_ORG_INFO = "CREATE TABLE " + TABLE_ORGANIZATION_INFO + " (" +
                C_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_U_UID + " INTEGER, " +
                C_ORG_NAME + " TEXT, " +
                C_ORG_ADDRESS + " TEXT " +
                ");";

        //USER EARNING TABLE CREATE QUERY
        private static final String CREATE_TABLE_USER_INCOME = "CREATE TABLE " + TABLE_WAGE + " (" +
                C_E_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_E_SHEET_UID + " INTEGER, " +
                C_E_USER_UID + " INTEGER, " +
                C_E_DATE + " TEXT, " +
                C_E_START_TIME + " TEXT, " +
                C_E_DURATION + " TEXT, " +
                C_E_WAGES + " DOUBLE " +
                ");";

        //SHEET INFO TABLE CREATE QUERY
        public static final String CREATE_TABLE_SHEET_INFO = "CREATE TABLE " + TABLE_SHEET + " (" +
                C_S_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_S_ORG_UID + " INTEGER, " +
                C_S_USER_UID + " INTEGER, " +
                C_S_NAME + " TEXT, " +
                C_S_ORG_NAME + " TEXT, " +
                C_S_ORG_ADDRESS + " TEXT, " +
                C_S_HOUR_RATE + "" +
                ");";

        //USER INFO TABLE CREATE QUERY
        public static final String CREATE_TABLE_USER_INFO = "CREATE TABLE " + TABLE_USER_INFO + " (" +
                C_U_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_U_NAME + " TEXT, " +
                C_U_EMAIL + " TEXT " +
                ");";

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_ORG_INFO); //TABLE ORG InFO
                db.execSQL(CREATE_TABLE_USER_INCOME); //USER INCOME TABLE CREATE
                db.execSQL(CREATE_TABLE_SHEET_INFO); //SHEET CREATE
                db.execSQL(CREATE_TABLE_USER_INFO); //SHEET CREATE
            } catch (SQLiteException exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(" DROP TABLE " + CREATE_TABLE_ORG_INFO + " IF EXISTS;");
                db.execSQL(" DROP TABLE " + CREATE_TABLE_USER_INCOME + " IF EXISTS;");//USER INCOME TABLE DROP IF ALREADY EXIST
                db.execSQL(" DROP TABLE " + CREATE_TABLE_SHEET_INFO + " IF EXISTS;"); //SHEET TABLE DROP IF EXIST IN APPLICATION
                db.execSQL(" DROP TABLE " + CREATE_TABLE_USER_INFO + " IF EXISTS;"); //SHEET TABLE DROP IF EXIST IN APPLICATION
                onCreate(db);
            } catch (SQLiteException exception) {
                exception.printStackTrace();
            }
        }
    }
}