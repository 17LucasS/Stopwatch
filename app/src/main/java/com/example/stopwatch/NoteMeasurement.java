package com.example.stopwatch;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="note_table")
public class NoteMeasurement {
    @ColumnInfo(name = "time_column")
    private String time;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public NoteMeasurement(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
