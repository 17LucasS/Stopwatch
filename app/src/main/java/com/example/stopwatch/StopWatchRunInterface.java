package com.example.stopwatch;

public interface StopWatchRunInterface {
    void sedDoRunning(Boolean doRunning);
    void startStopWatchRunning();
    void setMillisecond(long millisecond);
    void shutDownExecutors();

    void setDoRunSecondMillisecond(boolean doRunSecondMillisecond);
    void setSecondMillisecond(long millisecond);
}
