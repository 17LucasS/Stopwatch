package com.example.stopwatch;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="catch_time_table")
public class CatchTimeTable {
    @ColumnInfo(name = "time_column")
    private String catchTime;
    private String timeDifferences;
    private String overallTime;


    @PrimaryKey(autoGenerate = true)
    private int id;

    public CatchTimeTable(String catchTime,String timeDifferences,String overallTime) {
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
