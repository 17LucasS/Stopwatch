package com.example.stopwatch;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteMeasurement.class,NoteResults.class},version = 1)
public abstract class DataBaseWatch extends RoomDatabase {

    private static DataBaseWatch instance;

    public abstract NoteDaoMeasurement noteDaoMeasurement();
    public abstract NoteDaoResults noteDaoResults();

    public static synchronized DataBaseWatch getInstance(Context context){
        if (instance ==null){
            instance = Room.databaseBuilder
                    (context.getApplicationContext(),DataBaseWatch.class,"note_data")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
