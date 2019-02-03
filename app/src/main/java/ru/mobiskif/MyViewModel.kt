package ru.mobiskif

import androidx.lifecycle.*

class MyViewModel : ViewModel() {

    var pos_user = 1
    var pos_distr = 1
    var pos_lpu = 1
    var pos_spec = 1

    //var position = MutableLiveData<Int>(4)
    val distrlist = MutableLiveData<MutableList<Map<String, String>>>()
    val lpulist = MutableLiveData<MutableList<Map<String, String>>>()
    val speclist = MutableLiveData<MutableList<Map<String, String>>>()
    val doclist = MutableLiveData<MutableList<Map<String, String>>>()
    val talonlist = MutableLiveData<MutableList<Map<String, String>>>()

    private lateinit var cuser: MutableLiveData<Int>

    var cname = MutableLiveData<String>()
    var cfam = MutableLiveData<String>()
    var cotch = MutableLiveData<String>()
    var cdate = MutableLiveData<String>("")
    var cidPat = MutableLiveData<String>()
    var cidDoc = mutableMapOf<String, String>()
    var cidTalon = mutableMapOf<String, String>()
    var cidLpu = 1
    var cidSpec = 1

    fun setDistrlist() = Thread({ distrlist.postValue(Hub().GetDistr("GetDistrictList")) }).start()
    fun setLpulist() = Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
    fun setSpecList() = Thread({ speclist.postValue(Hub().GetSpec("GetSpesialityList", cidLpu)) }).start()

    fun setPatient() {
        val args = arrayOf(cname.value, cfam.value, cotch.value, cdate.value)
        Thread({ cidPat.postValue(Hub().GetPat("CheckPatient", cidLpu, args)["IdPat"]) }).start()
    }

    fun setDocList() {
        val args = arrayOf(cidLpu, cidSpec, cidPat.value)
        Thread({ doclist.postValue(Hub().GetDoc("GetDoctorList", args)) }).start()
    }

    fun setTalonList() {
        val args = arrayOf(cidLpu, cidDoc["IdDoc"], cidPat.value)
        Thread({ talonlist.postValue(Hub().GetTalons("GetAvaibleAppointments", args)) }).start()
    }

    fun getUserID(): LiveData<Int> {
        if (!::cuser.isInitialized) {
            cuser = MutableLiveData()
            Thread({ cuser.postValue(pos_user) }).start()
        }
        return cuser
    }


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


