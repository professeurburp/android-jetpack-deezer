package com.professeurburp.deezerapitest.data.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.professeurburp.deezerapitest.data.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> loadUser(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User newUser);
}
