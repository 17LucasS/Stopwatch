package com.example.stopwatch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    public MeasurementInterface measurementInterface;
    private final LiveData<List<NoteMeasurement>> listMeasurement;

    public SharedPreferenceInterface sharedPrefInterface;
    public StopWatchRunInterface stopWatchRunInterface;
     private final Repository repository;
    private final LiveData<String> time;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        time = repository.getTime();
        setRunInterface();
        setSharedPrefInterface();

        listMeasurement = repository.getAllMeasurements();
        measurementInterface = repository.measurementInterface;
    }

    public String sharedPreferenceGetStarButton(){
        return repository.sharedPreferenceGetStarButton();
    }

    public String sharedPreferenceGetCatchButton(){
        return repository.sharedPreferenceGetCatchButton();
    }

    public String sharedPreferenceGetTime(){
        return repository.sharedPreferenceGetTime();
    }

    public boolean sharedPreferenceGetIsRunning(){
        return repository.sharedPreferenceIsRunning();
    }

    public long sharedPreferenceGetMillisecond() {
        return repository.sharedPreferenceGetMillisecond();
    }

    public boolean sharedPreferenceGetCatchButtonIsEnable() {
        return repository.sharedPreferenceGetCatchButtonIsEnable();
    }
    private void setSharedPrefInterface(){
        this.sharedPrefInterface = repository.setSharedPrefInterface();
    }

    public void setRunInterface(){
        this.stopWatchRunInterface = repository.setRunInterface();
    }

    public boolean isDoRunning(){
        return repository.isDoRunning();
    }

    public Long getTMillisecond(){
        return repository.getTMillisecond();
    }


    public void startStopWatchRun(){
        stopWatchRunInterface.setMillisecond(System.currentTimeMillis());
        stopWatchRunInterface.sedDoRunning(true);
        stopWatchRunInterface.startStopWatchRunning();
    }

    public void resumeStopWatchRun(Long millisecond){
        stopWatchRunInterface.setMillisecond(millisecond);
        stopWatchRunInterface.sedDoRunning(true);
        stopWatchRunInterface.startStopWatchRunning();
    }

    public void savaData(String startB,String catchB,String time,
                         boolean doRun,boolean catchBEnable,long millisecond){
        sharedPrefInterface.saveData(SharedPreferenceData.ST_BUTTON,startB);
        sharedPrefInterface.saveData(SharedPreferenceData.CATCH_BUTTON,catchB);
        sharedPrefInterface.saveData(SharedPreferenceData.TIME_TEXT,time);
        sharedPrefInterface.saveData(SharedPreferenceData.DO_RUNNING_DATA,doRun);
        sharedPrefInterface.saveData(SharedPreferenceData.MILLISECOND,millisecond);
        sharedPrefInterface.saveData(SharedPreferenceData.CATCH_BUTTON_ENABLE,catchBEnable);
    }

    public LiveData<String> getTime(){
        return time;
    }
    public LiveData<List<NoteMeasurement>> getAllMeasurements(){
        return listMeasurement;
    }

}
