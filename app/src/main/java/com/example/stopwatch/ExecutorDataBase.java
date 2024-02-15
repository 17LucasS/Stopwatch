package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDataBase {

    private static ExecutorService service;
    private static ExecutorDataBase instance;
    private NoteDaoCatchTime noteDaoCatchTime;
    private LiveData<List<CatchTimeTable>> setCatchList;

    private NoteDaoMainActData noteDaoMainActData;
    private LiveData<List<MainActDataTable>> setLiveDataMainData;


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
        noteDaoMainActData = dataBaseWatch.noteDaoMainActData();
        this.setLiveDataMainData = noteDaoMainActData.getLiveDataMainData();
        noteDaoCatchTime = dataBaseWatch.noteDaoCatch();
        this.setCatchList = noteDaoCatchTime.getCatchList();
    }

    /*   CatchList   */

    public void deleteCatch() {
        service.execute(() -> noteDaoCatchTime.deleteAllNotes());
    }
    public void insertCatch(CatchTimeTable note) {
        service.execute(() -> noteDaoCatchTime.insert(note));
    }

    public LiveData<List<CatchTimeTable>> getCatchList() {
        return setCatchList;
    }


    /* MainActData methods */
    public void insertMainData(MainActDataTable data) {
        service.execute(() -> noteDaoMainActData.insertMainData(data));
    }

    public void deleteAllMainData() {
        service.execute(() -> noteDaoMainActData.deleteAllMainData());
    }

    public LiveData<List<MainActDataTable>> getMainDataList() {
        return setLiveDataMainData;
    }



    public void shutdownExecutorDataBase() {
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
