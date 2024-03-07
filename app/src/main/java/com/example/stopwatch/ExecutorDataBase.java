package com.example.stopwatch;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.stopwatch.stopWatch.StopWatchCatchTimeTable;
import com.example.stopwatch.stopWatch.NoteDaoStopWatchData;
import com.example.stopwatch.stopWatch.NoteDaoStopWatchCatchTime;
import com.example.stopwatch.stopWatch.StopWatchDataTable;
import com.example.stopwatch.stopWatchLaps.NoteDaoStoWatchLapsData;
import com.example.stopwatch.stopWatchLaps.NoteDaoStopWatchLapsCatch;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsCatchTable;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsDataTable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDataBase {

    private static ExecutorService service;
    private static ExecutorDataBase instance;
    /* StopWatch  */
    private NoteDaoStopWatchData noteDaoStopWatchData;
    private NoteDaoStopWatchCatchTime noteDaoStopWatchCatchTime;
    private LiveData<List<StopWatchDataTable>> setAllNoteStopWatchData;
    private LiveData<List<StopWatchCatchTimeTable>> setAllNoteStopWatchTime;

    /* StopWatchLaps */
    private NoteDaoStopWatchLapsCatch noteDaoStopWatchLapsCatch;
    private NoteDaoStoWatchLapsData noteDaoStoWatchLapsData;
    private LiveData<List<StopWatchLapsDataTable>> getLiveDataStopWatchLapsData;
    private LiveData<List<StopWatchLapsCatchTable>> getLiveDataStopWatchLapsCatch;



    public static synchronized ExecutorDataBase getInstance() {
        if (instance == null) {
            instance = new ExecutorDataBase();
        }
        if (service == null || service.isTerminated()) {
            service = Executors.newSingleThreadExecutor();
        }
        return instance;
    }

    public void createDataBase(Application application) {
        DataBaseWatch dataBaseWatch = DataBaseWatch.getInstance(application);
        /* StopWatch  */
        this.noteDaoStopWatchData = dataBaseWatch.noteDaoStopWatchData();
        this.setAllNoteStopWatchData = noteDaoStopWatchData.getAllNoteStopWatchData();
        this.noteDaoStopWatchCatchTime = dataBaseWatch.noteDaoTimeStopWatch();
        this.setAllNoteStopWatchTime = noteDaoStopWatchCatchTime.getAllNoteTimeStopWatch();
        /* StopWatchLaps */
        this.noteDaoStoWatchLapsData = dataBaseWatch.noteDaoStoWatchLapsData();
        this.noteDaoStopWatchLapsCatch = dataBaseWatch.noteDaoStopWatchLapsCatch();
        this.getLiveDataStopWatchLapsData = noteDaoStoWatchLapsData.getLiveDataStopWatchLapsData();
        this.getLiveDataStopWatchLapsCatch = noteDaoStopWatchLapsCatch.getLiveDataStopWatchLapsCatch();
    }

    /* StopWatchLaps*/
    public void insertStopWatchLapsData(StopWatchLapsDataTable table){
        service.execute(()->noteDaoStoWatchLapsData.insertStopWatchLapsData(table));
    }
     public void deleteStopWatchLapsData(){
        service.execute(()->noteDaoStoWatchLapsData.deleteStopWatchLapsData());
     }

    public LiveData<List<StopWatchLapsDataTable>> getLiveDataStopWatchLapsData(){
        return getLiveDataStopWatchLapsData;
    }


    public void insertStopWatchLapsCatch(StopWatchLapsCatchTable table){
        service.execute(()->noteDaoStopWatchLapsCatch.insertStopWatchLapsCatch(table));
    }


   public void deleteStopWatchLapsCatch(){
        service.execute(()->noteDaoStopWatchLapsCatch.deleteStopWatchLapsCatch());
   }


    public LiveData<List<StopWatchLapsCatchTable>> getLiveDataStopWatchLapsCatch(){
        return getLiveDataStopWatchLapsCatch;
    }



    /* StopWatch */
    public void insertStopWatchData(StopWatchDataTable note){
        service.execute(()->noteDaoStopWatchData.insertStopWatchData(note));
    }

    public void deleteStopWatchData(){
        service.execute(()->noteDaoStopWatchData.deleteStopWatchData());
    }

    public LiveData<List<StopWatchDataTable>> getAllNoteStopWatchData(){
        return setAllNoteStopWatchData;
    }

    void insertTimeStopWatch(StopWatchCatchTimeTable note){
        service.execute(()-> noteDaoStopWatchCatchTime.insertTimeStopWatch(note));
    }

    void deleteAllTimeStopWatch(){
        service.execute(()-> noteDaoStopWatchCatchTime.deleteAllTimeStopWatch());
    }
    public LiveData<List<StopWatchCatchTimeTable>> getAllNoteStopWatchTime(){
        return setAllNoteStopWatchTime;
    }

    public void checkIsServiceIsShutDownDataBase(){
        if (service.isShutdown() || service.isTerminated()){
            service = Executors.newSingleThreadExecutor();
        }
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
