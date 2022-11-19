package com.testproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.testproject.model.User
import com.testproject.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    private val firstName = MutableStateFlow("")
    private val lastName = MutableStateFlow("")

    fun setFirstName(name: String) {
        firstName.value = name
    }
    fun setLastName(name: String) {
        lastName.value = name
    }

    val isBtnEnabled: Flow<Boolean> = combine(firstName, lastName) {fName, lName ->
        val isFirstNameCorrect = fName.isNotEmpty()
        val isLastNameCorrect = lName.isNotEmpty()
        return@combine isFirstNameCorrect and isLastNameCorrect
    }

    val allUsers: Flow<List<User>> = repository.allUsers

    fun insertUser(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun deleteAllUser() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class UserViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}