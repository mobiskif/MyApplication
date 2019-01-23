package ru.mobiskif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    private lateinit var distrlist: MutableLiveData<List<String>>
    private lateinit var speclist: MutableLiveData<List<String>>
    private lateinit var lpulist: MutableLiveData<List<String>>
    private lateinit var cuser: MutableLiveData<Int>
    var pos_distr = 3
    var pos_user = 1
    var pos_lpu = 1
    var cdistr = MutableLiveData<String>("")
    var cname = MutableLiveData<String>("")
    var cfam = MutableLiveData<String>()
    var cotch = MutableLiveData<String>()
    var cdate = MutableLiveData<String>("qwe")

    init {

    }

    fun getUser(): LiveData<Int> {
        if (!::cuser.isInitialized) {
            cuser = MutableLiveData()
            //Thread({ cuser.postValue(1) }).start()
        }
        Thread({
            cname.postValue("jopa $pos_user")
            cfam.postValue("qweqweqwe $pos_user")
            cuser.postValue(pos_user)
        }).start()
        return cuser
    }
    fun getDistrlist(): LiveData<List<String>> {
        if (!::distrlist.isInitialized) {
            distrlist = MutableLiveData()
            Thread({ distrlist.postValue(Hub().GetDistr("GetDistrictList")) }).start()
        }
        return distrlist
    }

    fun getLpulist(): LiveData<List<String>> {
        if (!::lpulist.isInitialized) {
            lpulist = MutableLiveData()
            //Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList",cdistrict)) }).start()
        }
        Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList",pos_distr)) }).start()
        return lpulist
    }

    fun getSpeclist(): LiveData<List<String>> {
        if (!::speclist.isInitialized) {
            speclist = MutableLiveData()
            Thread({ speclist.postValue(Hub().GetSpec("GetSpesialityList", pos_lpu)) }).start()
        }
        return speclist
    }

}
