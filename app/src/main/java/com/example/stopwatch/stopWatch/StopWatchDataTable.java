package com.example.stopwatch.stopWatch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stop_watch_data_table")
public class StopWatchDataTable {
    @PrimaryKey
    private int id;
    private String time;
    private String startButton;
    private String catchButton;
    private boolean timeRunning;
    private long millisecond;
    private long saveMilli;
    private boolean catchEnable;
    private long millisecondWhenRun;

    public StopWatchDataTable(String time, String startButton, String catchButton, boolean timeRunning, long millisecond, long saveMilli, long millisecondWhenRun, boolean catchEnable) {
        this.id = 1;
        this.time = time;
        this.startButton = startButton;
        this.catchButton = catchButton;
        this.timeRunning = timeRunning;
        this.millisecond = millisecond;
        this.saveMilli = saveMilli;
        this.millisecondWhenRun = millisecondWhenRun;
        this.catchEnable = catchEnable;
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

    public String getStartButton() {
        return startButton;
    }

    public void setStartButton(String startButton) {
        this.startButton = startButton;
    }

    public String getCatchButton() {
        return catchButton;
    }

    public void setCatchButton(String catchButton) {
        this.catchButton = catchButton;
    }

    public boolean isTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(boolean timeRunning) {
        this.timeRunning = timeRunning;
    }

    public long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }

    public long getSaveMilli() {
        return saveMilli;
    }

    public void setSaveMilli(long saveMilli) {
        this.saveMilli = saveMilli;
    }

    public boolean isCatchEnable() {
        return catchEnable;
    }

    public void setCatchEnable(boolean catchEnable) {
        this.catchEnable = catchEnable;
    }

    public long getMillisecondWhenRun() {
        return millisecondWhenRun;
    }

    public void setMillisecondWhenRun(long millisecondWhenRun) {
        this.millisecondWhenRun = millisecondWhenRun;
    }
}
