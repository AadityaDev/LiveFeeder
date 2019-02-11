package com.aditya.livefeeder.db.dao;

import com.aditya.livefeeder.db.entities.Label;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LabelDAO {

    @Query("SELECT * FROM label")
    List<Label> getAll();

    @Insert
    void insertAll(Label... labels);

    @Delete
    void delete(Label label);

}
