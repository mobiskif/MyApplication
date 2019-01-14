package ru.mobiskif

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var context: Context? = null
    var cdistrict = 0
    var cuser: MutableLiveData<Int> = MutableLiveData()
    var cname: MutableLiveData<String> = MutableLiveData()
    var cfam: MutableLiveData<String> = MutableLiveData()
    var cotch: MutableLiveData<String> = MutableLiveData()
    var cdate: MutableLiveData<String> = MutableLiveData()
    var clpu: MutableLiveData<Int> = MutableLiveData()
    var clpuname: MutableLiveData<String> = MutableLiveData()
    var cspec: MutableLiveData<Int> = MutableLiveData()
    var cspecname: MutableLiveData<String> = MutableLiveData()
    var cdoctor: MutableLiveData<Int> = MutableLiveData()
    var cdoctorname: MutableLiveData<String> = MutableLiveData()

    private var districtList: MutableLiveData<List<String>>  = MutableLiveData()
    private var lpuList: MutableLiveData<List<String>> = MutableLiveData()
    private var doctorList: MutableLiveData<List<String>> = MutableLiveData()
    private var specialityList: MutableLiveData<List<String>> = MutableLiveData()
    private var talonList: MutableLiveData<List<Map<String, Any>>> = MutableLiveData()

    fun load() {
        loadUser(restorecurrent())
    }

    fun saveUser() {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        val eddef = defsettings.edit()
        eddef.putInt("currentUser", cuser.value!!)
        eddef.apply()

        istore(cuser.value!!, "district", cdistrict)
        sstore(cuser.value!!, "name", cname.value!!)
        sstore(cuser.value!!, "fam", cfam.value!!)
        sstore(cuser.value!!, "otch", cotch.value!!)
        sstore(cuser.value!!, "date", cdate.value!!)
        istore(cuser.value!!, "lpu", clpu.value!!)
        sstore(cuser.value!!, "lpuname", clpuname.value!!)
        istore(cuser.value!!, "spec", cspec.value!!)
        sstore(cuser.value!!, "specname", cspecname.value!!)
        istore(cuser.value!!, "doctor", cdoctor.value!!)
        sstore(cuser.value!!, "doctorname", cdoctorname.value!!)
    }

    fun loadUser(id: Int) {
        cname.value = srestore(id, "name")
        cfam.value = srestore(id, "fam")
        cotch.value = srestore(id, "otch")
        cdate.value = srestore(id, "date")
        cdoctor.value = irestore(id, "doctor")
        cdoctorname.value = srestore(id, "doctorname")
        cspec.value = irestore(id, "spec")
        cspecname.value = srestore(id, "specname")
        clpu.value = irestore(id, "lpu")
        clpuname.value = srestore(id, "lpuname")
        cdistrict = irestore(id, "district")
        cuser.value = id
    }

    private fun restorecurrent(): Int {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        return defsettings.getInt("currentUser", 1)
    }

    private fun irestore(id: Int, key: String): Int {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        return settings.getInt(key, 1)
    }

    private fun srestore(id: Int, key: String): String {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        return settings.getString(key, "")
    }

    private fun istore(id: Int, key: String, value: Int) {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        val ed = settings.edit()
        ed.putInt(key, value)
        ed.apply()
    }

    private fun sstore(id: Int, key: String, value: String) {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        val ed = settings.edit()
        ed.putString(key, value)
        ed.apply()
    }

    fun getDistrictList(): MutableLiveData<List<String>> {
        //if (!::districtList.isInitialized) districtList = MutableLiveData()
        districtList.value = context!!.resources.getStringArray(R.array.area).toMutableList()
        request("districtList")
        return districtList
    }

    fun getLpuList(): MutableLiveData<List<String>> {
        lpuList.value = context!!.resources.getStringArray(R.array.lpu).toMutableList()
        request("lpuList", cdistrict)
        return lpuList
    }

    fun getSpecialityList(): MutableLiveData<List<String>> {
        specialityList.value = context!!.resources.getStringArray(R.array.spec).toMutableList()
        request("specialityList", clpu.value)
        return specialityList
    }

    fun getDoctorList(): MutableLiveData<List<String>> {
        doctorList.value = context!!.resources.getStringArray(R.array.doc).toMutableList()
        request("doctorList", cspec.value)
        return doctorList
    }

    fun getTalonList(): MutableLiveData<List<Map<String, Any>>> {
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
        mt["1234-3"] = "16:56"
        mt["1234-4"] = "17:56"
        m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["День недели"] = "Вн"
        m["Дата"] = "29/12/19"
        m["Время работы"] = "15:30 - 18:45"
        mt = mutableMapOf()
        mt["12345-1"] = "14:56"
        mt["12345-2"] = "15:56"
        mt["12345-3"] = "16:56"
        mt["12345-4"] = "17:56"
        m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["День недели"] = "Ср"
        m["Дата"] = "30/12/19"
        m["Время работы"] = "17:00 - 18:45"
        mt = mutableMapOf()
        mt["123456-1"] = "14:56"
        mt["123456-2"] = "15:56"
        mt["123456-3"] = "16:56"
        mt["123456-4"] = "17:56"
        m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["День недели"] = "Чт"
        m["Дата"] = "30/12/19"
        m["Время работы"] = "17:00 - 18:45"
        mt = mutableMapOf()
        mt["1234567-1"] = "14:56"
        mt["1234567-2"] = "15:56"
        mt["1234567-3"] = "16:56"
        mt["1234567-4"] = "17:56"
        m["Талоны"] = mt
        ll.add(m)

        talonList.value = ll
        request("talonList", clpu.value)
        return talonList
    }

    fun getHistory(): MutableLiveData<List<Map<String, Any>>> {
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

        talonList.value = ll
        request("historyList", clpu.value)
        return talonList
    }

    private fun request(a: Any?, b: Any?=null) {
        Log.d("jop", "request ${a.toString()}(${b.toString()})")
    }

}





