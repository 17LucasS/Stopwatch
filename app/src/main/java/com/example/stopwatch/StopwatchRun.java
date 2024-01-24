package com.example.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StopwatchRun implements StopWatchRunInterface {


    private static StopwatchRun instance;
    private static ScheduledExecutorService service;
    private boolean doRunning;
    private long millisecond;
    private long tMillisecond;

    private MutableLiveData<List<Time>> setTimeList = new MutableLiveData<>();

    public static synchronized StopwatchRun getInstance() {
        if (instance == null) {
            instance = new StopwatchRun();
        }
        if (service == null) {
            service = Executors.newScheduledThreadPool(1);
        }
        return instance;
    }


    public LiveData<List<Time>> getTimeList() {
        return setTimeList;
    }

    @Override
    public void startStopWatchRunning() {
        Runnable task = () -> {
            if (doRunning) {
                long startMillisecond = System.currentTimeMillis();
                tMillisecond = startMillisecond - millisecond;
                int hours = (int) (tMillisecond / 3600000);
                int minutes = (int) ((tMillisecond % 3600000) / 60000);
                int seconds = (int) ((tMillisecond % 60000) / 1000);
                int milliseconds = (int) (tMillisecond % 1000) / 10;
                ArrayList<Time> list = new ArrayList<>();
                list.add(new Time(hours, minutes, seconds, milliseconds));
                setTimeList.postValue(list);
            }
        };
        if (doRunning) {
            service.scheduleAtFixedRate(task, 0, 30, TimeUnit.MILLISECONDS);
        } else {
            service.shutdown();
        }
    }

    @Override
    public void sedDoRunning(Boolean doRunning) {
        this.doRunning = doRunning;
    }

    @Override
    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }

    @Override
    public void shutDownExecutors() {
        service.shutdown();
        if (service != null) {
            try {
                if (!service.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                service.shutdownNow();
            }
        }
    }

}
