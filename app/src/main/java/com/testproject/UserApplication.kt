package com.testproject

import android.app.Application
import com.testproject.db.UserRoomDatabase
import com.testproject.repository.UserRepository

class UserApplication : Application() {
    private val userDatabase: UserRoomDatabase by lazy { UserRoomDatabase.getDBInstance(this) }
    val userRepository: UserRepository by lazy { UserRepository(userDatabase.getUserDao()) }
}