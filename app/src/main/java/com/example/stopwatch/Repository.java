package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.stopwatch.stopWatch.StopWatchCatchTimeTable;
import com.example.stopwatch.stopWatch.StopWatchDataTable;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsCatchTable;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsDataTable;

import java.util.List;


public class Repository {

    private final StopwatchRun stopwatchRun;
    private final LiveData<String> setTimeLiveData;
    private final LiveData<String> setCatchTimeLiveData;
    private final ExecutorDataBase executorDataBase;


    public Repository(Application application) {
        executorDataBase = ExecutorDataBase.getInstance();
        executorDataBase.createDataBase(application);
        stopwatchRun = StopwatchRun.getInstance();
        setTimeLiveData = stopwatchRun.getTimeLiveData();
        setCatchTimeLiveData = stopwatchRun.getCatchTimeLiveData();
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

    public long getUserMillisecond() {
        return stopwatchRun.getUserMillisecond();
    }

    public boolean getCatchRunning() {
        return stopwatchRun.getCatchRunning();
    }

    public long getCatchUserMillisecond() {
        return stopwatchRun.getCatchUserMillisecond();
    }


    /* StopWatch  methods*/
    public void insertTimeStopWatch(StopWatchCatchTimeTable note) {
        executorDataBase.insertTimeStopWatch(note);
    }

    public void deleteAllTimeStopWatch() {
        executorDataBase.deleteAllTimeStopWatch();
    }

    public LiveData<List<StopWatchCatchTimeTable>> getAllNoteTimeStopWatch() {
        return executorDataBase.getAllNoteStopWatchTime();
    }

    public void insertStopWatchData(StopWatchDataTable note) {
        executorDataBase.insertStopWatchData(note);
    }

    public void deleteStopWatchData() {
        executorDataBase.deleteStopWatchData();
    }

    public LiveData<List<StopWatchDataTable>> getAllNoteStopWatchData() {
        return executorDataBase.getAllNoteStopWatchData();
    }

    /* StopWatchLaps */

    public void insertDataStopWatchLaps(StopWatchLapsDataTable table) {
        executorDataBase.insertStopWatchLapsData(table);
    }

    public void deleteStopWatchLapsData() {
        executorDataBase.deleteStopWatchLapsData();
    }
    public LiveData<List<StopWatchLapsDataTable>> getLiveDataStopWatchLapsData(){
        return executorDataBase.getLiveDataStopWatchLapsData();
    }

    public void insertStopWatchLapsCatch(StopWatchLapsCatchTable table){
        executorDataBase.insertStopWatchLapsCatch(table);
    }


    public void deleteStopWatchLapsCatch(){
        executorDataBase.deleteStopWatchLapsCatch();
    }

    public LiveData<List<StopWatchLapsCatchTable>> getLiveDataStopWatchLapsCatch(){
        return executorDataBase.getLiveDataStopWatchLapsCatch();
    }


    /* DataBase*/

    public void checkIsServiceIsShutDownDataBase(){
        executorDataBase.checkIsServiceIsShutDownDataBase();
    }

    public void shutdownExecutorDataBase() {
        executorDataBase.shutdownExecutorDataBase();
    }
}


