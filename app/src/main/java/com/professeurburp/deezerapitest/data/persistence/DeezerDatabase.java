package com.professeurburp.deezerapitest.data.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.model.User;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumDetailsDao;
import com.professeurburp.deezerapitest.data.persistence.dao.AlbumOverviewDao;
import com.professeurburp.deezerapitest.data.persistence.dao.UserDao;

@Database(entities = {AlbumOverview.class, AlbumDetails.class, User.class}, version = 1)
public abstract class DeezerDatabase extends RoomDatabase {

    /**
     * Gets the AlbumOverview objects DAO
     *
     * @return AlbumOverviewDao
     */
    public abstract AlbumOverviewDao getAlbumDao();

    public abstract UserDao getUserDao();

    public abstract AlbumDetailsDao getAlbumDetailsDao();
}
