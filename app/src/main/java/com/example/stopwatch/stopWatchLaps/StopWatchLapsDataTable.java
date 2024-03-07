package com.example.stopwatch.stopWatchLaps;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stop_watch_laps_data_table")
public class StopWatchLapsDataTable {

    @PrimaryKey
    private int id;
    private String time, bestTime;
    private String startButton, catchButt;

    private String catchTime;
    private  boolean catchRunning;
    private long catchMillisecond,catchUserMillisecond,saveMill;


    private boolean timeRunning,catchTimeIsEnable;
    private long timeMillisecond,userMillisecond;

    public StopWatchLapsDataTable(String time, String bestTime,
                            String startButton, String catchButt, boolean timeRunning,
                            boolean catchTimeIsEnable, long timeMillisecond,long userMillisecond,
                            String catchTime,boolean catchRunning,long catchMillisecond,long catchUserMillisecond,long saveMill) {
        this.id=1;
        this.time = time;
        this.bestTime = bestTime;
        this.startButton = startButton;
        this.catchButt = catchButt;
        this.timeRunning = timeRunning;
        this.catchTimeIsEnable = catchTimeIsEnable;
        this.timeMillisecond = timeMillisecond;
        this.userMillisecond = userMillisecond;
        this.catchTime = catchTime;
        this.catchRunning = catchRunning;
        this.catchMillisecond = catchMillisecond;
        this.catchUserMillisecond = catchUserMillisecond;
        this.saveMill = saveMill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public String getStartButton() {
        return startButton;
    }

    public void setStartButton(String startButton) {
        this.startButton = startButton;
    }

    public String getCatchButt() {
        return catchButt;
    }

    public void setCatchButt(String catchButt) {
        this.catchButt = catchButt;
    }

    public boolean isTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(boolean timeRunning) {
        this.timeRunning = timeRunning;
    }

    public boolean isCatchTimeIsEnable() {
        return catchTimeIsEnable;
    }

    public void setCatchTimeIsEnable(boolean catchTimeIsEnable) {
        this.catchTimeIsEnable = catchTimeIsEnable;
    }

    public long getTimeMillisecond() {
        return timeMillisecond;
    }

    public void setTimeMillisecond(long timeMillisecond) {
        this.timeMillisecond = timeMillisecond;
    }

    public long getUserMillisecond() {
        return userMillisecond;
    }

    public void setUserMillisecond(long systemTimeMillisecond) {
        this.userMillisecond= systemTimeMillisecond;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }

    public boolean isCatchRunning() {
        return catchRunning;
    }

    public void setCatchRunning(boolean catchRunning) {
        this.catchRunning = catchRunning;
    }

    public long getCatchMillisecond() {
        return catchMillisecond;
    }

    public void setCatchMillisecond(long catchMillisecond) {
        this.catchMillisecond = catchMillisecond;
    }

    public long getCatchUserMillisecond() {
        return catchUserMillisecond;
    }

    public void setCatchUserMillisecond(long catchUserMillisecond) {
        this.catchUserMillisecond = catchUserMillisecond;
    }

    public long getSaveMill() {
        return saveMill;
    }

    public void setSaveMill(long saveMill) {
        this.saveMill = saveMill;
    }

}
