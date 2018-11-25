package ru.m

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class myViewModel : ViewModel() {
    private lateinit var users: MutableLiveData<List<String>>

    fun getUsers(): MutableLiveData<List<String>> {
        if (!::users.isInitialized) {
            users = MutableLiveData()
            users.setValue(listOf("qwe", "asd", "zxc"))
        }
        return users
    }

    fun getList(): List<String> {
        return listOf("qwe", "asd", "zxc")
    }
}



