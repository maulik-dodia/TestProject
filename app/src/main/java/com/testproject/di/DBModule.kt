package com.testproject.di

import android.content.Context
import androidx.room.Room
import com.testproject.dao.UserDao
import com.testproject.db.UserRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): UserRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            UserRoomDatabase::class.java,
            "user_info"
        ).build()
    }

    @Provides
    fun provideDao(database: UserRoomDatabase): UserDao {
        return database.getUserDao()
    }
}