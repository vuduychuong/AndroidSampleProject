package com.chuongvd.app.signal.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.chuongvd.app.signal.data.entity.UserEntity

@Dao
interface UserDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertUser(user: UserEntity)

  @Query("SELECT * FROM user LIMIT 1")
  fun loadUser(): LiveData<UserEntity>

  @Query("DELETE FROM user")
  fun deleteAllUser()
}
