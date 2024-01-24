package com.example.stopwatch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    StopWatchRunInterface runInterface;
    Repository repository;
    private LiveData<List<Time>> time;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository();
        time = repository.getTime();
        setRunInterface();
    }
    public void setRunInterface(){
        this.runInterface = repository.setStopwatchRunInterface();
    }

    public LiveData<List<Time>> getTime(){
        return time;
    }

}
