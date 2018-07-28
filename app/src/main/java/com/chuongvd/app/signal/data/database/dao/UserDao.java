package com.chuongvd.app.signal.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.chuongvd.app.signal.data.entity.UserEntity;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<UserEntity> loadUser();

    @Query("DELETE FROM user")
    void deleteAllUser();
}
