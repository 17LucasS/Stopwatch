package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StopwatchRun implements StopWatchRunInterface {
    private static StopwatchRun instance;
    private static ScheduledExecutorService service;
    private boolean doRunning;
    private long millisecond;
    private long secondMillisecond, tSecondMillisecond;
    private long tMillisecond;
    private boolean doRunSecondMillisecond;
    private final MutableLiveData<String> setTimeList = new MutableLiveData<>();
    private final MutableLiveData<String> setSecondTimeList = new MutableLiveData<>();

    public static synchronized StopwatchRun getInstance() {
        if (instance == null) {
            instance = new StopwatchRun();
        }
        if (service == null) {
            service = Executors.newScheduledThreadPool(1);
        }
        return instance;
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
                if (doRunSecondMillisecond) {
                    long startMill = System.currentTimeMillis();
                    tSecondMillisecond = startMill - secondMillisecond;
                    int hou = (int) (tSecondMillisecond / 3600000);
                    int min = (int) ((tSecondMillisecond  % 3600000) / 60000);
                    int sec = (int) ((tSecondMillisecond % 60000) / 1000);
                    int milli = (int) (tSecondMillisecond  % 1000) / 10;

                    @SuppressLint("DefaultLocale")
                    String secondTime = String.format("%02d:%02d:%02d:%02d", hou, min, sec, milli);
                    setSecondTimeList.postValue(secondTime);
                }

                @SuppressLint("DefaultLocale")
                String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds);
                setTimeList.postValue(time);
            }
        };
        if (doRunning) {
            service.scheduleAtFixedRate(task, 0, 70, TimeUnit.MILLISECONDS);
        } else {
            service.shutdown();
        }
    }

    public LiveData<String> getTimeList() {
        return setTimeList;
    }

    public LiveData<String> getSecondTimeList() {
        return setSecondTimeList;
    }


    @Override
    public void sedDoRunning(Boolean doRunning) {
        this.doRunning = doRunning;
    }

    @Override
    public void setDoRunSecondMillisecond(boolean doRunSecondMillisecond) {
        this.doRunSecondMillisecond = doRunSecondMillisecond;
    }

    @Override
    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }

    @Override
    public void setSecondMillisecond(long millisecond) {
        this.secondMillisecond = millisecond;
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

    public boolean isDoRunning() {
        return doRunning;
    }

    public boolean isDoRunSecondMillisecond() {
        return doRunSecondMillisecond;
    }

    public long getTMillisecond() {
        return tMillisecond;
    }

    public long getTSecondMill() {
        return tSecondMillisecond;
    }
}
