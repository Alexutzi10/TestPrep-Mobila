package com.example.mobila.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobila.data.Mobila;

import java.util.List;

@Dao
public interface MobilaDao {
    @Query("SELECT * FROM mobila")
    List<Mobila> getAll();

    @Insert
    List<Long> insertAll(List<Mobila> lista);

    @Delete
    int delete(List<Mobila> lista);
}
