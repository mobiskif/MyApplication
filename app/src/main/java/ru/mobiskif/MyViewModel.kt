package ru.mobiskif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    var pos_distr = 1
    var pos_user = 1
    var pos_lpu = 1
    var pos_spec = 1

    private lateinit var distrlist: MutableLiveData<List<String>>
    private lateinit var lpulist: MutableLiveData<MutableList<Map<String, String>>>
    private lateinit var cpatient: MutableLiveData<Map<String, String>>
    private lateinit var speclist: MutableLiveData<MutableList<Map<String, String>>>
    private lateinit var doclist: MutableLiveData<MutableList<Map<String, String>>>
    private lateinit var histlist: MutableLiveData<List<String>>
    private lateinit var talonlist: MutableLiveData<List<Map<String, Any>>>
    private lateinit var cuser: MutableLiveData<Int>

    var cname = MutableLiveData<String>()
    var cfam = MutableLiveData<String>()
    var cotch = MutableLiveData<String>()
    var cdate = MutableLiveData<String>("")
    var cidLPU = 175
    var cidSpec = 23

    fun getUser(): LiveData<Int> {
        if (!::cuser.isInitialized) {
            cuser = MutableLiveData()
            Thread({ cuser.postValue(pos_user) }).start()
        }
        return cuser
    }

    fun getDistrlist(): LiveData<List<String>> {
        if (!::distrlist.isInitialized) {
            distrlist = MutableLiveData()
            Thread({ distrlist.postValue(Hub().GetDistr("GetDistrictList")) }).start()
        }
        return distrlist
    }

    fun getLpulist(): MutableLiveData<MutableList<Map<String, String>>> {
        if (!::lpulist.isInitialized) {
            lpulist = MutableLiveData()
            Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
        }
        return lpulist
    }

    fun updateLpuList() {
        Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
    }

    fun getSpecList(): LiveData<MutableList<Map<String, String>>> {
        if (!::speclist.isInitialized) {
            speclist = MutableLiveData()
            Thread({ speclist.postValue(Hub().GetSpec("GetSpesialityList", cidLPU)) }).start()
        }
        return speclist
    }

    fun updateSpecList() {
        Thread({ speclist.postValue(Hub().GetSpec("GetSpesialityList", cidLPU)) }).start()
    }

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

    fun getDoctorList(): MutableLiveData<MutableList<Map<String, String>>> {
        if (!::doclist.isInitialized) {
            doclist = MutableLiveData()
            if(getPatient().value!=null) {
                val args = arrayOf(cidLPU, cidSpec, getPatient().value!!["IdPat"])
                Thread({ doclist.postValue(Hub().GetDoc("GetDoctorList", args)) }).start()
            }
        }
        return doclist
    }

    fun updateDocList() {
        val args = arrayOf(cidLPU, cidSpec, getPatient().value!!["IdPat"])
        Thread({ doclist.postValue(Hub().GetDoc("GetDoctorList", args)) }).start()
    }


    fun getPatient(): MutableLiveData<Map<String, String>> {
        if (!::cpatient.isInitialized) {
            cpatient = MutableLiveData()
            //var a = cpatient.value
            //a.set("IdPat","22")
            val args = arrayOf(cname.value, cfam.value, cotch.value, cdate.value)
            Thread({ cpatient.postValue(Hub().CheckPat("CheckPatient", cidLPU, args)) }).start()
        }
        return cpatient
    }

    fun checkPatient() {
        val args = arrayOf(cname.value, cfam.value, cotch.value, cdate.value)
        Thread({ cpatient.postValue(Hub().CheckPat("CheckPatient", cidLPU, args)) }).start()
    }


}
