package com.testproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testproject.dao.UserDao
import com.testproject.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDBInstance(context: Context): UserRoomDatabase {
            if(INSTANCE == null) {
                INSTANCE = synchronized(this) {
                    Room.databaseBuilder(context, UserRoomDatabase::class.java, "user_info").build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}