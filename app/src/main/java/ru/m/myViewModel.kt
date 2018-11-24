package ru.m

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class myViewModel : ViewModel() {
    private lateinit var users: MutableLiveData<List<String>>

    fun getUsers(): MutableLiveData<List<String>> {
        if (!::users.isInitialized) {
            users = MutableLiveData()
            loadUsers()
        }
        return users
    }

    private fun loadUsers() {
        users.setValue(listOf<String>("qwe","asd","zxc"))
    }
}



