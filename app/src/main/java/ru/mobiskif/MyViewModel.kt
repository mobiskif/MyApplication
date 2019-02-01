package ru.mobiskif

import androidx.lifecycle.*

class MyViewModel : ViewModel() {

    var pos_user = 1
    var pos_distr = 1
    var pos_lpu = 1
    var pos_spec = 1

    var position = MutableLiveData<Int>(4)

    private lateinit var cuser:     MutableLiveData<Int>
    private lateinit var distrlist: MutableLiveData<MutableList<Map<String, String>>>
    val lpulist = MutableLiveData<MutableList<Map<String, String>>>()
    val speclist = MutableLiveData<MutableList<Map<String, String>>>()
    private lateinit var doclist:   MutableLiveData<MutableList<Map<String, String>>>

    var cname = MutableLiveData<String>()
    var cfam = MutableLiveData<String>()
    var cotch = MutableLiveData<String>()
    var cdate = MutableLiveData<String>("")
    var cidPat = MutableLiveData<String>()
    var cidLpu = 1
    var cidSpec = 1

    fun setPosition(inp: Int) {
        position.postValue(inp+1)
    }


    fun getUserID(): LiveData<Int> {
        if (!::cuser.isInitialized) {
            cuser = MutableLiveData()
            Thread({ cuser.postValue(pos_user) }).start()
        }
        return cuser
    }

    fun getDistrlist(): MutableLiveData<MutableList<Map<String, String>>> {
        if (!::distrlist.isInitialized) {
            distrlist = MutableLiveData()
            Thread({ distrlist.postValue(Hub().GetDistr("GetDistrictList")) }).start()
        }
        return distrlist
    }


    fun setLpulist(){//: MutableLiveData<MutableList<Map<String, String>>> {
        //if (!::lpulist.isInitialized) {
            //lpulist = MutableLiveData()
            Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
        //}
        //return lpulist
    }

    fun updateLpulist() {
        Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
    }

    fun getPatient(): MutableLiveData<String> {
        //cidPat = MutableLiveData()
        val args = arrayOf(cname.value, cfam.value, cotch.value, cdate.value)
        Thread({ cidPat.postValue(Hub().GetPat("CheckPatient", cidLpu, args)["IdPat"]) }).start()
        return cidPat
    }

    /*
    private lateinit var histlist: MutableLiveData<List<String>>
    private lateinit var talonlist: MutableLiveData<List<Map<String, Any>>>

        fun getHistoryList(): MutableLiveData<List<Map<String, Any>>> {
            talonlist = MutableLiveData<List<Map<String, Any>>>()
            val ll = mutableListOf<Map<String, Any>>()
            var tal = mutableListOf<Map<String, String>>()
            var m: MutableMap<String, Any> = mutableMapOf()
            var mt: MutableMap<String, String> = mutableMapOf()


            m = mutableMapOf()
            m["День недели"] = "Пн"
            m["Дата"] = "28/12/19"
            m["Время работы"] = "14:30 - 18:45"

            mt = mutableMapOf()
            mt["1234-1"] = "14:56"
            mt["1234-2"] = "15:56"
            m["Талоны"] = mt
            ll.add(m)

            m = mutableMapOf()
            m["День недели"] = "Ср"
            m["Дата"] = "30/12/19"
            m["Время работы"] = "17:00 - 18:45"
            mt = mutableMapOf()
            mt["123456-4"] = "17:56"
            m["Талоны"] = mt
            ll.add(m)

            talonlist.value = ll
            //request("historyList", clpu.value)
            return talonlist
        }
    */

    fun setSpecList() {//: LiveData<MutableList<Map<String, String>>> {
        //if (!::speclist.isInitialized) {
            //speclist = MutableLiveData()
        Thread({ speclist.postValue(Hub().GetSpec("GetSpesialityList", cidLpu)) }).start()
    }

    fun updateSpecList() {
    }

    fun getDocList(): MutableLiveData<MutableList<Map<String, String>>> {
        if (!::doclist.isInitialized) {
            doclist = MutableLiveData()
            updateDocList()
        }
        return doclist
    }

    fun updateDocList() {
        if (getPatient().value != null) {
            val args = arrayOf(cidLpu, cidSpec, getPatient().value)
            Thread({ doclist.postValue(Hub().GetDoc("GetDoctorList", args)) }).start()
        }
    }

}
