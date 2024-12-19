package com.example.mobila.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mobila.data.DateConverter;
import com.example.mobila.data.Mobila;

@Database(entities = {Mobila.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    public static DatabaseManager connection;

    public static DatabaseManager getInstance(Context context) {
        if (connection != null) {
            return connection;
        }

        connection = Room.databaseBuilder(context, DatabaseManager.class, "mobila_db").fallbackToDestructiveMigration().build();

        return connection;
    }

    public abstract MobilaDao getMobilaDao();
}
