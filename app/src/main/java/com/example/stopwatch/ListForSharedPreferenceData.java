package com.example.stopwatch;

public class ListForSharedPreferenceData {
    private String startButton;
    private String catchButton;
    private long time;
    private boolean doRunning;

    public ListForSharedPreferenceData(String startButton, String catchButton, long time, boolean doRunning) {
        this.startButton = startButton;
        this.catchButton = catchButton;
        this.time = time;
        this.doRunning = doRunning;
    }

    public String getStartButton() {
        return startButton;
    }

    public String getCatchButton() {
        return catchButton;
    }

    public long getTime() {
        return time;
    }

    public boolean isDoRunning() {
        return doRunning;
    }
}
