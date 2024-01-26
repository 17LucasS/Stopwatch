package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;



public class Repository {

    private final SharedPreferenceData sharedPreferenceData;
    private final StopwatchRun stopwatchRun;
    private final LiveData<String> time;
    public Repository(Application application){
        stopwatchRun = StopwatchRun.getInstance();
        sharedPreferenceData = SharedPreferenceData.getInstance(application);
        time = stopwatchRun.getTimeList();
    }

    public StopWatchRunInterface setRunInterface(){
        return stopwatchRun;
    }

    public SharedPreferenceInterface setSharedPrefInterface(){
        return sharedPreferenceData;
    }

    public String sharedPreferenceGetStarButton(){
        return sharedPreferenceData.sharedPreferenceGetStarButton();
    }

    public String sharedPreferenceGetCatchButton(){
        return sharedPreferenceData.sharedPreferenceGetCatchButton();
    }

    public String sharedPreferenceGetTime(){
        return sharedPreferenceData.sharedPreferenceGetTime();
    }

    public boolean sharedPreferenceIsRunning(){
        return sharedPreferenceData.sharedPreferenceIsRunning();
    }

    public long sharedPreferenceGetMillisecond() {
        return sharedPreferenceData.sharedPreferenceGetMillisecond();
    }

    public boolean sharedPreferenceGetCatchButtonIsEnable() {
        return sharedPreferenceData.sharedPreferenceGetCatchButtonIsEnable();
    }

    public boolean isDoRunning(){
        return stopwatchRun.isDoRunning();
    }

    public Long getTMillisecond(){
        return stopwatchRun.getTMillisecond();
    }


    public LiveData<String> getTime(){
        return time;
    }

}
