package com.example.stopwatch;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StopwatchRun {
    private static StopwatchRun instance;
    private static ScheduledExecutorService service;
    private boolean timeRunning;
    private long userMillisecond;
    private long catchUserMillisecond, catchMillisecond;
    private long timeMillisecond;
    private boolean catchRunning;
    private final MutableLiveData<String> setTime = new MutableLiveData<>();
    private final MutableLiveData<String> setCatchTime = new MutableLiveData<>();

    public static synchronized StopwatchRun getInstance() {
        if (instance == null) {
            instance = new StopwatchRun();
        }
        if (service == null) {
            service = Executors.newScheduledThreadPool(1);
        }
        return instance;
    }

    public void startStopWatchRunning() {
        Runnable task = () -> {
            if (timeRunning) {
                long startMillisecond = System.currentTimeMillis();
                timeMillisecond = startMillisecond - userMillisecond;
                int hours = (int) (timeMillisecond / 3600000);
                int minutes = (int) ((timeMillisecond % 3600000) / 60000);
                int seconds = (int) ((timeMillisecond % 60000) / 1000);
                int milliseconds = (int) (timeMillisecond % 1000) / 10;
                if (catchRunning) {
                    long startMill = System.currentTimeMillis();
                    catchMillisecond = startMill - catchUserMillisecond;
                    int hou = (int) (catchMillisecond / 3600000);
                    int min = (int) ((catchMillisecond % 3600000) / 60000);
                    int sec = (int) ((catchMillisecond % 60000) / 1000);
                    int milli = (int) (catchMillisecond % 1000) / 10;

                    @SuppressLint("DefaultLocale")
                    String secondTime = String.format("%02d:%02d:%02d:%02d", hou, min, sec, milli);
                    setCatchTime.postValue(secondTime);
                }

                @SuppressLint("DefaultLocale")
                String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds);
                setTime.postValue(time);
            }
        };
        if (timeRunning) {
            service.scheduleAtFixedRate(task, 0, 70, TimeUnit.MILLISECONDS);
        } else {
            service.shutdown();
        }
    }

    public long getCatchUserMillisecond() {
        return catchUserMillisecond;
    }

    public LiveData<String> getTimeLiveData() {
        return setTime;
    }

    public LiveData<String> getCatchTimeLiveData() {
        return setCatchTime;
    }


    public void setTimeRunning(Boolean timeRunning) {
        this.timeRunning = timeRunning;
    }


    public void setCatchRunning(boolean catchRunning) {
        this.catchRunning = catchRunning;
    }

    public void setTimeUserMillisecond(long timeMillisecond) {
        this.userMillisecond = timeMillisecond;
    }

    public void setCatchUserMillisecond(long catchUserMillisecond) {
        this.catchUserMillisecond = catchUserMillisecond;
    }

    public void shutDownStopWatchExecutors() {
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

    public boolean getTimeRunning() {
        return timeRunning;
    }

    public boolean getCatchRunning() {
        return catchRunning;
    }

    public long getTimeMillisecond() {
        return timeMillisecond;
    }

    public long getCatchMillisecond() {
        return catchMillisecond;
    }

    public long getUserMillisecond() {
        return userMillisecond;
    }
}
