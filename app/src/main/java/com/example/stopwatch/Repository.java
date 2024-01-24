package com.example.stopwatch;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    StopWatchRunInterface stopWatchRunInterface;

    public StopwatchRun stopwatchRun;
    private LiveData<List<Time>> time;
    public Repository(){
        stopwatchRun = StopwatchRun.getInstance();
        time = stopwatchRun.getTimeList();
    }

    public StopWatchRunInterface setStopwatchRunInterface(){
        this.stopWatchRunInterface = stopwatchRun;
        return stopWatchRunInterface;
    }

    public LiveData<List<Time>> getTime(){
        return time;
    }

}
