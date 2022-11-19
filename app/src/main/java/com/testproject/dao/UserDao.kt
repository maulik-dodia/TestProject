package com.testproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testproject.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM User ORDER BY userId ASC")
    fun getAllUsers() : Flow<List<User>>

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}