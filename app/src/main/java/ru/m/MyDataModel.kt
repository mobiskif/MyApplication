package ru.m

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyDataModel : ViewModel() {
    val J = "jop"
    var context: Context? = null
    var cdistrict=0
    lateinit var cid: MutableLiveData<Int>
    lateinit var cname: MutableLiveData<String>
    private lateinit var districtList: MutableLiveData<List<String>>
    private lateinit var lpuList: MutableLiveData<List<String>>
    private lateinit var doctorList: MutableLiveData<List<String>>
    private lateinit var specialityList: MutableLiveData<List<String>>

    fun init(c: Context?) {
        context = c
        if (!::cid.isInitialized) {
            cid = MutableLiveData()
            //cid.value=1
        }
        if (!::cname.isInitialized) cname = MutableLiveData()
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
            1 -> lpuList.setValue(listOf<String>("Поликлиника 11", "Больница 12", "Лаборатория 10", "Скорая помощь 01"))
            2 -> lpuList.setValue(listOf<String>("Поликлиника 21", "Больница 22", "Лаборатория 20", "Скорая помощь 02"))
            3 -> lpuList.setValue(listOf<String>("Поликлиника 31", "Больница 32", "Лаборатория 30", "Скорая помощь 03"))
            else -> lpuList.setValue(listOf<String>("Поликлиника", "Больница", "Лаборатория", "Скорая помощь"))
        }
        return lpuList
    }

    fun getDoctorList(): MutableLiveData<List<String>> {
        if (!::doctorList.isInitialized) {
            doctorList = MutableLiveData()
            doctorList.setValue(listOf<String>("Иванов А.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
        }
        return doctorList
    }

    fun getSpecialityList(): MutableLiveData<List<String>> {
        if (!::specialityList.isInitialized) {
            specialityList = MutableLiveData()
            specialityList.setValue(listOf<String>("Терапевт", "Хирург", "Гинеколог"))
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





