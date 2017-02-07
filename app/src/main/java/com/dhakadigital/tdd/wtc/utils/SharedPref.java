package com.dhakadigital.tdd.wtc.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by y34h1a on 2/7/17.
 */

public class SharedPref {
    // LogCat tag
    private static String TAG = SharedPref.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "SessionContent";

    private static final String KEY_START_TIME = "key_start_time";
    private static final String KEY_IS_STARTED = "key_is_started";

    public SharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        //TODO fix this warning
        editor = pref.edit();
    }

    public void setStartTime(String startTime){
        editor.putString(KEY_START_TIME, startTime);
        editor.commit();
    }
    public String  getStartTime(){
        return pref.getString(KEY_START_TIME,"00:00:00");

    }

    public void setKeyStarted(boolean isStarted){
        editor.putBoolean(KEY_IS_STARTED, isStarted);
        editor.commit();
    }

    public boolean isStarted(){
        return pref.getBoolean(KEY_IS_STARTED,false);
    }


}
