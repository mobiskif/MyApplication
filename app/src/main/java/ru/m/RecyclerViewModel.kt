package ru.m

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecyclerViewModel : ViewModel() {

    private lateinit var mList1: MutableLiveData<List<String>>
    private lateinit var mList2: MutableLiveData<List<String>>

    fun getList1(): MutableLiveData<List<String>> {
        if (!::mList1.isInitialized) {
            mList1 = MutableLiveData()
            mList1.setValue(listOf<String>("Conan Doyle, Arthur", "Christie, Agatha", "Collins, Wilkie"))
        }
        return mList1
    }

    fun getList2(): MutableLiveData<List<String>> {
        if (!::mList2.isInitialized) {
            mList2 = MutableLiveData()
            mList2.setValue(listOf<String>("qwerqwer", "asdfas", "xcvzx", "qwer235qwer", "asd253432fas", "xc34525vzx"))
        }
        return mList2
    }

    fun update(){
        mList1.setValue(listOf<String>("Жопа, ${System.currentTimeMillis()}", "Попа, Попа", "Collins, ${System.currentTimeMillis()}"))
    }


}





