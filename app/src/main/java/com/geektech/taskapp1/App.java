package com.geektech.taskapp1;

import android.app.Application;

import androidx.room.Room;

import com.geektech.taskapp1.room.AppDatabase;

public class App extends Application {

    public static App instance;
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
    public static AppDatabase getDatabase() {
        return database;
    }
}
