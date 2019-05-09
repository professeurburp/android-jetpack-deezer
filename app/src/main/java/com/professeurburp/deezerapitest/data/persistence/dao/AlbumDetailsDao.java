package com.professeurburp.deezerapitest.data.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.professeurburp.deezerapitest.data.model.AlbumDetails;

import java.util.List;

@Dao
public interface AlbumDetailsDao {

    @Query("SELECT * FROM albumDetails WHERE id = :albumId")
    LiveData<AlbumDetails> getAlbumDetails(int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumDetails(AlbumDetails albumDetails);
}
