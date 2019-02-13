package com.aditya.livefeeder.db;

import com.aditya.livefeeder.db.dao.RepoDetailDAO;
import com.aditya.livefeeder.db.entities.RepoDetail;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepoDetail.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    public abstract RepoDetailDAO repoDetailDAO();

}
