package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class Repository {
    public MeasurementInterface measurementInterface;

    private final SharedPreferenceData sharedPreferenceData;
    private final StopwatchRun stopwatchRun;
    private final LiveData<String> time;
    private final LiveData<String> secondTime;
    private final LiveData<List<NoteMeasurement>> listMeasurement;

    public Repository(Application application) {
        stopwatchRun = StopwatchRun.getInstance();
        sharedPreferenceData = SharedPreferenceData.getInstance(application);
        time = stopwatchRun.getTimeList();
        secondTime = stopwatchRun.getSecondTimeList();

        ExecutorDataBase executorDataBase = ExecutorDataBase.getInstance();
        executorDataBase.createDataBase(application);
        listMeasurement = executorDataBase.getAllNotesMeasurement();
        measurementInterface = executorDataBase;
    }

    public StopWatchRunInterface setRunInterface() {
        return stopwatchRun;
    }

    public SharedPreferenceInterface setSharedPrefInterface() {
        return sharedPreferenceData;
    }

    public String sharedPreferenceGetStarButton() {
        return sharedPreferenceData.sharedPreferenceGetStarButton();
    }

    public String sharedPreferenceGetCatchButton() {
        return sharedPreferenceData.sharedPreferenceGetCatchButton();
    }

    public String sharedPreferenceGetTime() {
        return sharedPreferenceData.sharedPreferenceGetTime();
    }

    public boolean sharedPreferenceIsRunning() {
        return sharedPreferenceData.sharedPreferenceIsRunning();
    }

    public long sharedPreferenceGetMillisecond() {
        return sharedPreferenceData.sharedPreferenceGetMillisecond();
    }

    public boolean sharedPreferenceGetCatchButtonIsEnable() {
        return sharedPreferenceData.sharedPreferenceGetCatchButtonIsEnable();
    }

    public boolean isDoRunning() {
        return stopwatchRun.isDoRunning();
    }

    public Long getTMillisecond() {
        return stopwatchRun.getTMillisecond();
    }
    public long getTSecondMill(){
        return stopwatchRun.getTSecondMill();
    }


    public LiveData<String> getTime() {
        return time;
    }
    public LiveData<String> getSecondTimeList(){
        return secondTime;
    }

    public LiveData<List<NoteMeasurement>> getAllMeasurements() {
        return listMeasurement;
    }

}
