package com.example.stopwatch;

public interface SharedPreferenceInterface {

    void saveData(String key, boolean value);

    void saveData(String key, long value);

    void saveData(String key, String value);

}
