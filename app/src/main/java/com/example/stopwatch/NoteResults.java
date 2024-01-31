package com.example.stopwatch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_results_table")
public class NoteResults {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String time;

    public NoteResults(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
