package com.example.stopwatch;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPreferenceData implements SharedPreferenceInterface {

    private static SharedPreferenceData instance;
    public static final String ST_BUTTON = "start_button";
    public static final String CATCH_BUTTON = "catch_button";
    public static final String CATCH_BUTTON_ENABLE = "catch_button_enable";
    public static final String TIME_TEXT = "Time_text";
    public static final String DO_RUNNING_DATA = "do_running";
    public static final String MILLISECOND = "millisecond";
    private static SharedPreferences sharedPreferenceData;
    private static final String PREFS_NAME = "shared_data";

    public static synchronized SharedPreferenceData getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceData(context);
            sharedPreferenceData = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public SharedPreferenceData(Context context) {
        sharedPreferenceData = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferenceData.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void saveData(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferenceData.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @Override
    public void saveData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferenceData.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public String sharedPreferenceGetStarButton() {
        return sharedPreferenceData.getString(ST_BUTTON, "Start");
    }

    public String sharedPreferenceGetCatchButton() {
        return sharedPreferenceData.getString(CATCH_BUTTON, "Pomiar");
    }

    public String sharedPreferenceGetTime() {
        return sharedPreferenceData.getString(TIME_TEXT, "00:00:00:00");
    }

    public boolean sharedPreferenceIsRunning() {
        return sharedPreferenceData.getBoolean(DO_RUNNING_DATA, false);
    }

    public boolean sharedPreferenceGetCatchButtonIsEnable() {
        return sharedPreferenceData.getBoolean(CATCH_BUTTON_ENABLE, false);
    }

    public long sharedPreferenceGetMillisecond() {
        return sharedPreferenceData.getLong(MILLISECOND, 0);
    }

}
