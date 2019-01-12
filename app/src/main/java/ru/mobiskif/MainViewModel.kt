package ru.mobiskif

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_1.*
import java.util.*

class MainViewModel : ViewModel() {
    val J = "jop"
    var context: Context? = null
    var cdistrict = 0
    lateinit var cuser: MutableLiveData<Int>

    lateinit var cname: MutableLiveData<String>
    lateinit var cfam: MutableLiveData<String>
    lateinit var cotch: MutableLiveData<String>
    lateinit var cdate: MutableLiveData<String>

    lateinit var clpu: MutableLiveData<Int>
    lateinit var clpuname: MutableLiveData<String>
    lateinit var cspec: MutableLiveData<Int>
    lateinit var cspecname: MutableLiveData<String>
    lateinit var cdoctor: MutableLiveData<Int>
    lateinit var cdoctorname: MutableLiveData<String>

    private lateinit var districtList: MutableLiveData<List<String>>
    private lateinit var lpuList: MutableLiveData<List<String>>
    private lateinit var doctorList: MutableLiveData<List<String>>
    private lateinit var specialityList: MutableLiveData<List<String>>
    private lateinit var talonList: MutableLiveData<List<Map<String, Any>>>

    fun setOwner(ow: LifecycleOwner) {
        ow?.let {
            clpu.observe(it, Observer<Any> {
                Log.d(J, "Сработал наблюдатель clpu")
            })
            cspec.observe(it, Observer<Any> {
                Log.d(J, "Сработал наблюдатель cspec")
            })
            cdoctor.observe(it, Observer<Any> {
                Log.d(J, "Сработал наблюдатель cdoctor")
            })
        }
    }

    fun init() {
        if (!::cuser.isInitialized) cuser = MutableLiveData()
        if (!::cname.isInitialized) cname = MutableLiveData()
        if (!::clpu.isInitialized) clpu = MutableLiveData()
        if (!::clpuname.isInitialized) clpuname = MutableLiveData()
        if (!::cfam.isInitialized) cfam = MutableLiveData()
        if (!::cotch.isInitialized) cotch = MutableLiveData()
        if (!::cdate.isInitialized) cdate = MutableLiveData()
        if (!::cdoctor.isInitialized) cdoctor = MutableLiveData()
        if (!::cdoctorname.isInitialized) cdoctorname = MutableLiveData()
        if (!::cspec.isInitialized) cspec = MutableLiveData()
        if (!::cspecname.isInitialized) cspecname = MutableLiveData()
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
        if (!::districtList.isInitialized) {
            districtList = MutableLiveData()
            //districtList.setValue(listOf<String>("Кировский", "Приморский", "Московский", "Невский"))
            districtList.value = context!!.getResources().getStringArray(R.array.area).toMutableList()

        }
        request("districtList", null)
        return districtList
    }

    fun getLpuList(): MutableLiveData<List<String>> {
        if (!::lpuList.isInitialized) lpuList = MutableLiveData()
        /*
        when (cdistrict) {
            1 -> lpuList.setValue(listOf<String>("Поликлиника 1", "Больница 1", "Лаборатория 1", "Скорая помощь 1"))
            2 -> lpuList.setValue(listOf<String>("Поликлиника 2", "Больница 2", "Лаборатория 2", "Скорая помощь 2"))
            3 -> lpuList.setValue(listOf<String>("Поликлиника 3", "Больница 3", "Лаборатория 3", "Скорая помощь 3"))
            else -> lpuList.setValue(listOf<String>("Поликлиника", "Больница", "Лаборатория", "Скорая помощь"))
        }
        */

        lpuList.value = context!!.getResources().getStringArray(R.array.lpu).toMutableList()
        request("lpuList", cdistrict)
        return lpuList
    }

    fun getSpecialityList(): MutableLiveData<List<String>> {
        if (!::specialityList.isInitialized) specialityList = MutableLiveData()
        /*
        when (clpu.value) {
            1 -> specialityList.setValue(listOf<String>("Терапевт 1", "Хирург 1", "Гинеколог 1", "Окулист 1"))
            2 -> specialityList.setValue(listOf<String>("Терапевт 2", "Хирург 2", "Гинеколог 2", "Окулист 2"))
            3 -> specialityList.setValue(listOf<String>("Терапевт 3", "Хирург 3", "Гинеколог 3", "Окулист 3"))
            else -> specialityList.setValue(listOf<String>("Терапевт", "Хирург", "Гинеколог", "Окулист"))
        }
        */
        specialityList.value = context!!.getResources().getStringArray(R.array.spec).toMutableList()
        request("specialityList", clpu.value)
        return specialityList
    }

    fun getDoctorList(): MutableLiveData<List<String>> {
        if (!::doctorList.isInitialized) doctorList = MutableLiveData()
        /*
        when (cspec.value) {
            1 -> doctorList.setValue(listOf<String>("Иванов1 А.", "Петров1 П.", "Сидоров1 Г.", "Лифшиц1 Б.", "Хрущев Н.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
            2 -> doctorList.setValue(listOf<String>("Иванов2 А.", "Петров2 П.", "Сидоров2 Г.", "Лифшиц2 Б.", "Хрущев Н.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
            3 -> doctorList.setValue(listOf<String>("Иванов3 А.", "Петров3 П.", "Сидоров3 Г.", "Лифшиц3 Б.", "Хрущев Н.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
            else -> doctorList.setValue(listOf<String>("Иванов А.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
        }
        */
        doctorList.value = context!!.getResources().getStringArray(R.array.doc).toMutableList()
        request("doctorList", cspec.value)
        return doctorList
    }

    fun getTalonList(): MutableLiveData<List<Map<String, Any>>> {
        if (!::talonList.isInitialized) talonList = MutableLiveData()
        //talonList.setValue(listOf<String>("Talon 1", "Talon 2", "Talon 3", "Talon 4", "Talon 5", "Talon 6", "Talon 7"))
        //talonList.value = context!!.getResources().getStringArray(R.array.week).toMutableList()

        var ll = mutableListOf<Map<String, Any>>()
        var tal = mutableListOf<Map<String, String>>()
        var m: MutableMap<String, Any> = mutableMapOf()
        var mt: MutableMap<String, String> = mutableMapOf()


        m = mutableMapOf()
        m.put("День недели","Пн")
        m.put("Дата","28/12/19")
        m.put("Время работы","14:30 - 18:45")

        mt = mutableMapOf()
        mt.put("1234-1", "14:56")
        mt.put("1234-2", "15:56")
        mt.put("1234-3", "16:56")
        mt.put("1234-4", "17:56")
        m.put("Талоны", mt)
        ll.add(m)

        m = mutableMapOf()
        m.put("День недели","Вн")
        m.put("Дата","29/12/19")
        m.put("Время работы","15:30 - 18:45")
        mt = mutableMapOf()
        mt.put("12345-1", "14:56")
        mt.put("12345-2", "15:56")
        mt.put("12345-3", "16:56")
        mt.put("12345-4", "17:56")
        m.put("Талоны", mt)
        ll.add(m)

        m = mutableMapOf()
        m.put("День недели","Ср")
        m.put("Дата","30/12/19")
        m.put("Время работы","17:00 - 18:45")
        mt = mutableMapOf()
        mt.put("123456-1", "14:56")
        mt.put("123456-2", "15:56")
        mt.put("123456-3", "16:56")
        mt.put("123456-4", "17:56")
        m.put("Талоны", mt)
        ll.add(m)

        m = mutableMapOf()
        m.put("День недели","Чт")
        m.put("Дата","30/12/19")
        m.put("Время работы","17:00 - 18:45")
        mt = mutableMapOf()
        mt.put("1234567-1", "14:56")
        mt.put("1234567-2", "15:56")
        mt.put("1234567-3", "16:56")
        mt.put("1234567-4", "17:56")
        m.put("Талоны", mt)
        ll.add(m)

        talonList.value = ll
        request("talonList", clpu.value)
        return talonList
    }

    fun request(a: Any?, b: Any?) {
        Log.d(J, "request ${a.toString()}(${b.toString()})")
    }

}





