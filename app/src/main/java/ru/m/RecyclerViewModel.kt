package ru.m

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecyclerViewModel : ViewModel() {

    private lateinit var mList: MutableLiveData<List<String>>

    fun getList(): MutableLiveData<List<String>> {
        if (!::mList.isInitialized) {
            mList = MutableLiveData()
            mList.setValue(listOf<String>("Conan Doyle, Arthur", "Christie, Agatha", "Collins, Wilkie"))
        }
        return mList
    }

    fun update(){
        mList.setValue(listOf<String>("Жопа, ${System.currentTimeMillis()}", "Попа, Попа", "Collins, ${System.currentTimeMillis()}"))
    }


}





