package com.example.stopwatch;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    public StopWatchRunInterface runInterface;

    public StopwatchRun stopwatchRun;
    private LiveData<List<Time>> time;
    public Repository(){
        stopwatchRun = StopwatchRun.getInstance();
        time = stopwatchRun.getTimeList();
    }

    public StopWatchRunInterface setStopwatchRunInterface(){
        this.runInterface = stopwatchRun;
        return runInterface;
    }

    public LiveData<List<Time>> getTime(){
        return time;
    }

}
