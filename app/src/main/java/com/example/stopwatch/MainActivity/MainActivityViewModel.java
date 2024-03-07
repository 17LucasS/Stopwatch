package com.example.stopwatch.MainActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.stopwatch.Repository;

public class MainActivityViewModel extends AndroidViewModel {


    private final Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public void shutDownStopWatchExecutors() {
        repository.shutDownStopWatchExecutors();
    }

    public void shutdownExecutorDataBase() {
        repository.shutdownExecutorDataBase();
    }
}
