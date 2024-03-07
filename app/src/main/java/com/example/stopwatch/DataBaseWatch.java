package com.example.stopwatch;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.stopwatch.stopWatch.NoteDaoStopWatchCatchTime;
import com.example.stopwatch.stopWatch.StopWatchCatchTimeTable;
import com.example.stopwatch.stopWatch.NoteDaoStopWatchData;
import com.example.stopwatch.stopWatch.StopWatchDataTable;
import com.example.stopwatch.stopWatchLaps.NoteDaoStoWatchLapsData;
import com.example.stopwatch.stopWatchLaps.NoteDaoStopWatchLapsCatch;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsCatchTable;
import com.example.stopwatch.stopWatchLaps.StopWatchLapsDataTable;

@Database(entities = {StopWatchDataTable.class, StopWatchCatchTimeTable.class,
        StopWatchLapsDataTable.class, StopWatchLapsCatchTable.class},version = 1)
public abstract class DataBaseWatch extends RoomDatabase {
    private static DataBaseWatch instance;

    public abstract NoteDaoStopWatchData noteDaoStopWatchData();
    public abstract NoteDaoStopWatchCatchTime noteDaoTimeStopWatch();

    public abstract NoteDaoStoWatchLapsData noteDaoStoWatchLapsData();
    public abstract NoteDaoStopWatchLapsCatch noteDaoStopWatchLapsCatch();

    public static synchronized DataBaseWatch getInstance(Application application){
        if (instance ==null){
            instance = Room.databaseBuilder
                            (application.getApplicationContext(),DataBaseWatch.class,"note_data")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
