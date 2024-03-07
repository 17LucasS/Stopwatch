package com.example.stopwatch.stopWatch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stop_watch_catch_time_table")
public class StopWatchCatchTimeTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String time;
    private  String lostTime;

    public StopWatchCatchTimeTable(String time, String lostTime) {
        this.time = time;
        this.lostTime = lostTime;
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

    public String getLostTime() {
        return lostTime;
    }

    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }
}
