package com.example.stopwatch.stopWatchLaps;
import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stopwatch.Repository;

import java.util.List;

public class StopWatchLapsMvvM extends AndroidViewModel {


    private final Repository repository;
    private final LiveData<String> setTimeLiveData;
    private final LiveData<String> setCatchTimeLiveData;
    public StopWatchLapsMvvM(@NonNull Application application) {
        super(application);

        this.repository = new Repository(application);

        setTimeLiveData = repository.getTimeLiveData();
        setCatchTimeLiveData = repository.getCatchTimeLiveData();
    }

    /* StopWatchLaps fragment*/

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
        return secondTime.charAt(0)+secondTime.substring(1).replace("-","0");
    }

    public void setupStopWatchRunWhenAppWasRun(boolean timeRunning, long systemTime) {
        if (timeRunning) {
            repository.setTimeUserMillisecond(systemTime);
            startStopWatchRun();
        } else {
            repository.setTimeRunning(false);
        }

    }

    public void setupStopWatchRunWhenAppWasRunCatch(boolean catchRunning, long catchMillisecond, long userMillisecond) {
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



    /* StopwatchRun methods   */

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


    /* DataBase*/
    public void insertDataStopWatchLaps(StopWatchLapsDataTable table){
        repository.insertDataStopWatchLaps(table);
    }

    void deleteStopWatchLapsData(){
        repository.deleteStopWatchLapsData();
    }

    public LiveData<List<StopWatchLapsDataTable>> getLiveDataStopWatchLapsData(){
        return repository.getLiveDataStopWatchLapsData();
    }

    public void insertStopWatchLapsCatch(StopWatchLapsCatchTable table){
        repository.insertStopWatchLapsCatch(table);
    }


    public void deleteStopWatchLapsCatch(){
        repository.deleteStopWatchLapsCatch();
    }

    public LiveData<List<StopWatchLapsCatchTable>> getLiveDataStopWatchLapsCatch(){
        return repository.getLiveDataStopWatchLapsCatch();
    }



    public void shutdownExecutorDataBase() {
        repository.shutdownExecutorDataBase();
    }

    public void checkIsServiceIsShutDownDataBase(){
        repository.checkIsServiceIsShutDownDataBase();
    }
}
