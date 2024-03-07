package com.example.stopwatch.stopWatchLaps;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="stop_watch_laps_catch_table")
public class StopWatchLapsCatchTable {
    private String catchTime;
    private String timeDifferences;
    private String overallTime;


    @PrimaryKey(autoGenerate = true)
    private int id;

    public StopWatchLapsCatchTable(String catchTime, String timeDifferences, String overallTime) {
        this.catchTime = catchTime;
        this.timeDifferences = timeDifferences;
        this.overallTime = overallTime;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public int getId() {
        return id;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }

    public String getTimeDifferences() {
        return timeDifferences;
    }

    public void setTimeDifferences(String timeDifferences) {
        this.timeDifferences = timeDifferences;
    }

    public String getOverallTime() {
        return overallTime;
    }

    public void setOverallTime(String overallTime) {
        this.overallTime = overallTime;
    }
}
