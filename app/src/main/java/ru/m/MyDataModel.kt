package ru.m

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyDataModel : ViewModel() {
    private lateinit var districtList: MutableLiveData<List<String>>
    private lateinit var lpuList: MutableLiveData<List<String>>
    private lateinit var specialityList: MutableLiveData<List<String>>
    private lateinit var doctorList: MutableLiveData<List<String>>
    private lateinit var currentName: MutableLiveData<String>
    private lateinit var currentDistrict: MutableLiveData<Int>

    var context: Context? = null
    private var currentUser = 1
    //private var currentDistrict = 1
    var currentLPU = "0"
    var currentSpeciality = "0"
    var currentDoctor = "0"


    fun store() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //Storage.store(context,"currentName", currentName)

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
        when (getCurrentDistrict().value) {
            1 -> lpuList.setValue(listOf<String>("Поликлиника 11", "Больница 12", "Лаборатория 10", "Скорая помощь 01"))
            2 -> lpuList.setValue(listOf<String>("Поликлиника 21", "Больница 22", "Лаборатория 20", "Скорая помощь 02"))
            3 -> lpuList.setValue(listOf<String>("Поликлиника 31", "Больница 32", "Лаборатория 30", "Скорая помощь 03"))
            else -> lpuList.setValue(listOf<String>("Поликлиника", "Больница", "Лаборатория", "Скорая помощь"))
        }
        return lpuList
    }

    fun getSpecialityList(): MutableLiveData<List<String>> {
        if (!::specialityList.isInitialized) {
            specialityList = MutableLiveData()
            specialityList.setValue(listOf<String>("Хирург", "Терапевт", "Уролог", "Кардиолог", "Гинеколог", "Окулист"))
        }
        return specialityList
    }

    fun getDoctorList(): MutableLiveData<List<String>> {
        if (!::doctorList.isInitialized) {
            doctorList = MutableLiveData()
            doctorList.setValue(listOf<String>("Иванов А.", "Петров П.", "Сидоров Г.", "Лифшиц Б.", "Хрущев Н."))
        }
        return doctorList
    }

    fun getName(): MutableLiveData<String> {
        if (!::currentName.isInitialized) currentName = MutableLiveData()
        currentName.setValue("user=${getCurrentUser()}")
        return currentName
    }

    fun setCurrentUser(idUser: Int) {
        currentUser=idUser
        getName()
    }

    fun getCurrentUser(): Int {
        return currentUser
    }

    fun setDistrict(position: Int) {
        currentDistrict.value = position
        getLpuList()
    }

    fun getCurrentDistrict(): MutableLiveData<Int> {
        if (!::currentDistrict.isInitialized) currentDistrict = MutableLiveData()
        currentDistrict.setValue(1)
        return currentDistrict
    }

}





