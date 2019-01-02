package ru.m

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MyDataModel : ViewModel() {
    val J = "jop"
    var context: Context? = null
    var cdistrict=0
    //var clpu=0
    lateinit var cid: MutableLiveData<Int>
    lateinit var clpu: MutableLiveData<Int>
    lateinit var cname: MutableLiveData<String>
    private lateinit var districtList: MutableLiveData<List<String>>
    private lateinit var lpuList: MutableLiveData<List<String>>
    private lateinit var doctorList: MutableLiveData<List<String>>
    private lateinit var specialityList: MutableLiveData<List<String>>


    fun init(c: Context?) {
        context = c
        if (!::cid.isInitialized) cid = MutableLiveData()
        if (!::cname.isInitialized) cname = MutableLiveData()
        if (!::clpu.isInitialized) clpu = MutableLiveData()
        loadUser(restorecurrent())
    }

    fun saveUser() {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        val eddef = defsettings.edit()
        eddef.putInt("currentUser", cid.value!!)
        eddef.apply()

        istore(cid.value!!, "district", cdistrict)
        sstore(cid.value!!, "name", cname.value!!)
    }

    fun loadUser(id: Int) {
        cname.value = srestore(id, "name")
        cdistrict=irestore(id, "district")
        cid.value = id
    }

    private fun restorecurrent(): Int {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        return defsettings.getInt("currentUser", 1)
    }

    private fun irestore(id: Int, key: String): Int {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        return settings.getInt(key,1)
    }

    private fun srestore(id: Int, key: String): String {
        val settings = context!!.getSharedPreferences(id.toString(), 0)
        return settings.getString(key,"")
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
        if (!::districtList.isInitialized) {
            districtList = MutableLiveData()
            districtList.setValue(listOf<String>("Кировский", "Приморский", "Московский", "Невский"))
        }
        return districtList
    }

    fun getLpuList(): MutableLiveData<List<String>> {
        if (!::lpuList.isInitialized) lpuList = MutableLiveData()
        when (cdistrict) {
            1 -> lpuList.setValue(listOf<String>("Поликлиника 1", "Больница 1", "Лаборатория 1", "Скорая помощь 1"))
            2 -> lpuList.setValue(listOf<String>("Поликлиника 2", "Больница 2", "Лаборатория 2", "Скорая помощь 2"))
            3 -> lpuList.setValue(listOf<String>("Поликлиника 3", "Больница 3", "Лаборатория 3", "Скорая помощь 3"))
            else -> lpuList.setValue(listOf<String>("Поликлиника", "Больница", "Лаборатория", "Скорая помощь"))
        }
        return lpuList
    }

    fun getDoctorList(): MutableLiveData<List<String>> {
        if (!::doctorList.isInitialized) {
            doctorList = MutableLiveData()
            doctorList.setValue(listOf<String>("Иванов А.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
        }
        return doctorList
    }

    fun getSpecialityList(): MutableLiveData<List<String>> {
        if (!::specialityList.isInitialized) specialityList = MutableLiveData()
        when (cdistrict) {
            1 -> specialityList.setValue(listOf<String>("Терапевт 1", "Хирург 1", "Гинеколог 1", "Окулист 1"))
            2 -> specialityList.setValue(listOf<String>("Терапевт 2", "Хирург 2", "Гинеколог 2", "Окулист 2"))
            3 -> specialityList.setValue(listOf<String>("Терапевт 3", "Хирург 3", "Гинеколог 3", "Окулист 3"))
            else -> specialityList.setValue(listOf<String>("Терапевт", "Хирург", "Гинеколог", "Окулист"))
        }
        return specialityList
    }

/*
    fun getDistrict(): MutableLiveData<Int> {
        if (!::currentDistrict.isInitialized) {
            currentDistrict = MutableLiveData()
        }
        currentDistrict.setValue(Storage.restoreint(context,"currentDistrict"))
        return currentDistrict
    }
*/

}





