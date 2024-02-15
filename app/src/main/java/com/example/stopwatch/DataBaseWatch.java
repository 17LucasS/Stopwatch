package com.example.stopwatch;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CatchTimeTable.class,NoteResults.class, MainActDataTable.class},version = 1)
public abstract class DataBaseWatch extends RoomDatabase {

    private static DataBaseWatch instance;

    public abstract NoteDaoCatchTime noteDaoCatch();
    public abstract NoteDaoResults noteDaoResults();

    public abstract NoteDaoMainActData noteDaoMainActData();

    public static synchronized DataBaseWatch getInstance(Context context){
        if (instance ==null){
            instance = Room.databaseBuilder
                    (context.getApplicationContext(),DataBaseWatch.class,"note_data")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
