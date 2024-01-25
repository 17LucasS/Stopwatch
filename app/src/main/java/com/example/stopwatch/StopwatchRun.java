package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;

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
    private MutableLiveData<String> setTimeList = new MutableLiveData<>();

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

                @SuppressLint("DefaultLocale")
                String time = String.format("%02d:%02d:%02d:%02d",hours,minutes,seconds,milliseconds);
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

    public boolean isDoRunning() {
        return doRunning;
    }

    public long gettMillisecond() {
        return tMillisecond;
    }
}
