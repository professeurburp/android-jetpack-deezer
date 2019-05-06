package com.professeurburp.deezerapitest.data.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;

@Database(entities = {AlbumOverview.class}, version = 1)
public abstract class DeezerDatabase extends RoomDatabase {

    /**
     * Gets the AlbumOverview objects DAO
     * @return AlbumOverviewDao
     */
    public abstract AlbumOverviewDao albumDao();
}
