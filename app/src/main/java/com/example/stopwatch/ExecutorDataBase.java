package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDataBase implements MeasurementInterface {

    private static ExecutorService service;
    private static ExecutorDataBase instance;
    private NoteDaoMeasurement noteDaoMeasurement;
    private LiveData<List<NoteMeasurement>> listLiveData;


    public static synchronized ExecutorDataBase getInstance() {
        if (instance == null) {
            instance = new ExecutorDataBase();
        }
        if (service == null) {
            service = Executors.newSingleThreadExecutor();
        }
        return instance;
    }

    public void createDataBase(Application application) {
        DataBaseWatch dataBaseWatch = DataBaseWatch.getInstance(application);
        noteDaoMeasurement = dataBaseWatch.noteDaoMeasurement();
        this.listLiveData = noteDaoMeasurement.getAllNotes();
    }

    public LiveData<List<NoteMeasurement>> getAllNotesMeasurement() {
        return listLiveData;
    }


    @Override
    public void deleteMeasurement() {
        delete();
    }

    @Override
    public void insertMeasurement(NoteMeasurement note) {
        insert(note);
    }

    private void delete() {
        service.execute(() -> noteDaoMeasurement.deleteAllNotes());
    }

    private void insert(NoteMeasurement note) {
        service.execute(() -> noteDaoMeasurement.insert(note));
    }

    @Override
    public void shutdownExecutorMeasurement() {
        if (service != null) {
            service.shutdown();
            try {
                if (service.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                service.shutdownNow();
            }
        }

    }


}
