package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private final LiveData<List<MainActDataTable>> setMainDataList;

    private final LiveData<List<CatchTimeTable>> setCatchList;
    private final Repository repository;
    private final LiveData<String> setTimeLiveData;
    private final LiveData<String> setCatchTimeLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        setMainDataList = repository.getMainDataList();
        setTimeLiveData = repository.getTimeLiveData();
        setCatchTimeLiveData = repository.getCatchTimeLiveData();
        setCatchList = repository.getCatchList();

    }
    /* Main methods*/

    public String returnDifferencesTime(long millisecond) {
        long timeMillisecond;
        long startMillisecond = System.currentTimeMillis();
        timeMillisecond = startMillisecond - millisecond;
        int hours = (int) (timeMillisecond / 3600000);
        int minutes = (int) ((timeMillisecond % 3600000) / 60000);
        int seconds = (int) ((timeMillisecond % 60000) / 1000);
        int milliseconds = (int) (timeMillisecond % 1000) / 10;

        String sign = (hours < 0 || minutes < 0 || seconds < 0 || milliseconds < 0) ? "-" : "+";


        @SuppressLint("DefaultLocale")
        String secondTime = String.format("%s%02d:%02d:%02d:%02d", sign, Math.abs(hours),
                Math.abs(minutes), Math.abs(seconds), Math.abs(milliseconds));
        String correctTime = secondTime.charAt(0)+secondTime.substring(1).replace("-","0");
        return correctTime;
    }

    public void setupStopWatchWhenAppStartRun(boolean timeRunning, long systemTime) {
        if (timeRunning) {
            repository.setTimeUserMillisecond(systemTime);
            repository.setTimeRunning(true);
            repository.startStopWatchRunning();
        } else {
            repository.setTimeRunning(false);
        }

    }

    public void setupStopWatchWhenAppStartRunCatch(boolean catchRunning, long catchMillisecond, long userMillisecond) {
        if (catchRunning) {
            repository.setCatchUserMillisecond(userMillisecond);
            repository.setCatchRunning(true);
        }

    }

    public void catchTimeWasActive(boolean catchTimeWasActive, long catchTimeMillisecond) {
        if (catchTimeWasActive) {
            repository.setCatchUserMillisecond(System.currentTimeMillis() - catchTimeMillisecond);
            repository.setCatchRunning(true);
        }
    }

    public void startStopWatchRun() {
        repository.setTimeUserMillisecond(System.currentTimeMillis());
        repository.setTimeRunning(true);
        repository.startStopWatchRunning();
    }

    public void resetStopWatchRun() {
        repository.setTimeRunning(false);
        repository.setCatchRunning(false);
    }

    public void resumeStopWatchRun(Long millisecond) {
        repository.setTimeUserMillisecond(millisecond);
        repository.setTimeRunning(true);
        repository.startStopWatchRunning();
    }

    /*  MainActData Methods */
    public void insertMainData(MainActDataTable data) {
        repository.insertMainData(data);
    }

    public void deleteAllMainData() {
        repository.deleteAllMainData();
    }

    public LiveData<List<MainActDataTable>> getMainDataList() {
        return setMainDataList;
    }


    /* StopwatchRun methods   */
    public void startStopWatchRunning() {
        repository.startStopWatchRunning();
    }

    public void setTimeRunning(Boolean timeRunning) {
        repository.setTimeRunning(timeRunning);
    }

    public void setCatchRunning(boolean catchRunning) {
        repository.setCatchRunning(catchRunning);
    }

    public void setCatchUserMillisecond(long catchUserMillisecond) {
        repository.setCatchUserMillisecond(catchUserMillisecond);
    }

    public void setTimeUserMillisecond(long timeMillisecond) {
        repository.setTimeUserMillisecond(timeMillisecond);
    }

    public LiveData<String> getTimeLiveData() {
        return setTimeLiveData;
    }

    public LiveData<String> getCatchTimeLiveData() {
        return setCatchTimeLiveData;
    }


    public boolean getTimeRunning() {
        return repository.getTimeRunning();
    }

    public Long getTimeMillisecond() {
        return repository.getTimeMillisecond();
    }

    public long getCatchMillisecond() {
        return repository.getCatchMillisecond();
    }

    public long getUserMillisecond() {
        return repository.getUserMillisecond();
    }

    public void shutDownStopWatchExecutors() {
        repository.shutDownStopWatchExecutors();
    }

    public boolean getCatchRunning() {
        return repository.getCatchRunning();
    }

    public long getCatchUserMillisecond() {
        return repository.getCatchUserMillisecond();
    }


    /*   CatchList    */

    public void deleteCatch() {
        repository.deleteCatch();
    }

    public void insertCatch(CatchTimeTable note) {
        repository.insertCatch(note);
    }

    public void shutdownExecutorDataBase() {
        repository.shutdownExecutorDataBase();
    }

    public LiveData<List<CatchTimeTable>> getCatchList() {
        return setCatchList;
    }
}
