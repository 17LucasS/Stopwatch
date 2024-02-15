package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class Repository {
    private final LiveData<List<MainActDataTable>> setMainDataList;

    private final StopwatchRun stopwatchRun;
    private final LiveData<String> setTimeLiveData;
    private final LiveData<String> setCatchTimeLiveData;
    private final LiveData<List<CatchTimeTable>> setCatchList;
    private final ExecutorDataBase executorDataBase;

    public Repository(Application application) {
        executorDataBase = ExecutorDataBase.getInstance();
        executorDataBase.createDataBase(application);
        stopwatchRun = StopwatchRun.getInstance();
        setTimeLiveData = stopwatchRun.getTimeLiveData();
        setCatchTimeLiveData = stopwatchRun.getCatchTimeLiveData();
        this.setMainDataList = executorDataBase.getMainDataList();
        setCatchList = executorDataBase.getCatchList();
    }

    /*    MainActData Methods */
    public void insertMainData(MainActDataTable data) {
        executorDataBase.insertMainData(data);
    }

    public void deleteAllMainData() {
        executorDataBase.deleteAllMainData();
    }

    public LiveData<List<MainActDataTable>> getMainDataList() {
        return setMainDataList;
    }


    /*  StopWatchRun methods*/

    public void startStopWatchRunning() {
        stopwatchRun.startStopWatchRunning();
    }

    public void setTimeRunning(Boolean timeRunning) {
        stopwatchRun.setTimeRunning(timeRunning);
    }

    public void setCatchRunning(boolean catchRunning) {
        stopwatchRun.setCatchRunning(catchRunning);
    }

    public void setTimeUserMillisecond(long timeMillisecond) {
        stopwatchRun.setTimeUserMillisecond(timeMillisecond);
    }

    public void setCatchUserMillisecond(long catchUserMillisecond) {
        stopwatchRun.setCatchUserMillisecond(catchUserMillisecond);
    }

    public void shutDownStopWatchExecutors() {
        stopwatchRun.shutDownStopWatchExecutors();
    }


    public boolean getTimeRunning() {
        return stopwatchRun.getTimeRunning();
    }

    public long getTimeMillisecond() {
        return stopwatchRun.getTimeMillisecond();
    }

    public long getCatchMillisecond() {
        return stopwatchRun.getCatchMillisecond();
    }


    public LiveData<String> getTimeLiveData() {
        return setTimeLiveData;
    }

    public LiveData<String> getCatchTimeLiveData() {
        return setCatchTimeLiveData;
    }
    public long getUserMillisecond(){
        return stopwatchRun.getUserMillisecond();
    }
    public boolean getCatchRunning(){
       return stopwatchRun.getCatchRunning();
    }
    public long getCatchUserMillisecond(){
        return stopwatchRun.getCatchUserMillisecond();
    }


    /*      CatchList               */
    public void deleteCatch() {
        executorDataBase.deleteCatch();
    }

    public void insertCatch(CatchTimeTable note) {
        executorDataBase.insertCatch(note);
    }

    public void shutdownExecutorDataBase() {
        executorDataBase.shutdownExecutorDataBase();
    }

    public LiveData<List<CatchTimeTable>> getCatchList() {
        return setCatchList;
    }

}
