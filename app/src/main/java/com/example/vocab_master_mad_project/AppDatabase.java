package com.example.vocab_master_mad_project;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Vocab.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract VocabDao vocabDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "vocab_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Note: For simplicity in this project, we use allowMainThreadQueries. In a production app, use background threads.
                    .build();
        }
        return instance;
    }
}