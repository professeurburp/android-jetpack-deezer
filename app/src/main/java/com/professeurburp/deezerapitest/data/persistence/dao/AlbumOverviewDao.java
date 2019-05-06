package com.professeurburp.deezerapitest.data.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.professeurburp.deezerapitest.data.model.AlbumOverview;

import java.util.List;

@Dao
public interface AlbumOverviewDao {

    @Query("SELECT * FROM AlbumOverview")
    LiveData<List<AlbumOverview>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumsOverview(List<AlbumOverview> albumList);
}