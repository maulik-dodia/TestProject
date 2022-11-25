package com.testproject.repository

import com.testproject.dao.UserDao
import com.testproject.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    fun insert(user: User) = CoroutineScope(Dispatchers.IO).launch {
        userDao.insert(user)
    }

    fun deleteAll() = CoroutineScope(Dispatchers.IO).launch {
        userDao.deleteAll()
    }
}