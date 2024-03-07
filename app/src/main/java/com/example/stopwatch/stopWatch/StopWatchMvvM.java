package com.example.stopwatch.stopWatch;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stopwatch.Repository;

import java.util.List;

public class StopWatchMvvM extends AndroidViewModel {

    private final Repository repository;

    private final LiveData<String> getTimeLiveData;

    public StopWatchMvvM(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        getTimeLiveData = repository.getTimeLiveData();
    }

    /* Fragment methods*/
    public void setupStopWatchRunWhenAppWasRun(long millisecond) {
        repository.setTimeUserMillisecond(millisecond);
        repository.setTimeRunning(true);
        repository.startStopWatchRunning();
    }

    public void setStartButton() {
        repository.setTimeUserMillisecond(System.currentTimeMillis());
        repository.setTimeRunning(true);
        repository.startStopWatchRunning();
    }

    public void setStopButton() {
        repository.setTimeRunning(false);
    }

    public void setResumeButton(long millisecond) {
        repository.setTimeUserMillisecond(millisecond);
        repository.setTimeRunning(true);
        repository.startStopWatchRunning();
    }

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
        return secondTime.charAt(0) + secondTime.substring(1).replace("-", "0");
    }

    public void saveData(StopWatchDataTable note) {
        repository.insertStopWatchData(note);
    }


    /*  StopWatchRun methods*/

    public void startStopWatchRunning() {
        repository.startStopWatchRunning();
    }

    public void setTimeRunning(Boolean timeRunning) {
        repository.setTimeRunning(timeRunning);
    }

    public void setTimeUserMillisecond(long timeMillisecond) {
        repository.setTimeUserMillisecond(timeMillisecond);
    }

    public long getUserMillisecond() {
        return repository.getUserMillisecond();
    }

    public void shutDownStopWatchExecutors() {
        repository.shutDownStopWatchExecutors();
    }


    public boolean getTimeRunning() {
        return repository.getTimeRunning();
    }

    public long getTimeMillisecond() {
        return repository.getTimeMillisecond();
    }

    public LiveData<String> getTimeLiveData() {
        return getTimeLiveData;
    }

    public void setCatchRunning(boolean catchRunning) {
        repository.setCatchRunning(catchRunning);
    }


    /*DataBase methods*/

    public void insertTimeStopWatch(StopWatchCatchTimeTable note) {
        repository.insertTimeStopWatch(note);
    }

    public void deleteAllTimeStopWatch() {
        repository.deleteAllTimeStopWatch();
    }

    LiveData<List<StopWatchCatchTimeTable>> getAllNoteTimeStopWatch() {
        return repository.getAllNoteTimeStopWatch();
    }


    public void insertStopWatchData(StopWatchDataTable note) {
        repository.insertStopWatchData(note);
    }

    public void deleteStopWatchData() {
        repository.deleteStopWatchData();
    }

    public LiveData<List<StopWatchDataTable>> getAllNoteStopWatchData() {
        return repository.getAllNoteStopWatchData();
    }

    public void shutdownExecutorDataBase() {
        repository.shutdownExecutorDataBase();
    }

    public void checkIsServiceIsShutDownDataBase() {
        repository.checkIsServiceIsShutDownDataBase();
    }

}


