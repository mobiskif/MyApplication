package ru.m

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyDataModel : ViewModel() {
    private lateinit var districtList: MutableLiveData<List<String>>
    private lateinit var lpuList: MutableLiveData<List<String>>
    private lateinit var specialityList: MutableLiveData<List<String>>
    private lateinit var doctorList: MutableLiveData<List<String>>
    var context:Context? = null
    var currentUser="0"
    var currentName="0"
    var currentDistrict="0"
    var currentLPU="0"
    var currentSpeciality="0"
    var currentDoctor="0"


    fun getDistrictList(): MutableLiveData<List<String>> {
        if (!::districtList.isInitialized) {
            districtList = MutableLiveData()
            districtList.setValue(listOf<String>("Кировский", "Приморский", "Московский", "Невский"))
        }
        return districtList
    }

    fun getLpuList(): MutableLiveData<List<String>> {
        if (!::lpuList.isInitialized) {
            lpuList = MutableLiveData()
            lpuList.setValue(listOf<String>("Поликлиника 11", "Больница 12", "Лаборатория 1", "Скорая помощь"))
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


    fun updateLpuList() {
        if (!::lpuList.isInitialized) lpuList = MutableLiveData()
        if (currentDistrict=="1") lpuList.setValue(listOf<String>("Поликлиника 11 ${System.currentTimeMillis()}", "Больница 12 ${System.currentTimeMillis()}", "Лаборатория 1 ${System.currentTimeMillis()}", "Скорая помощь ${System.currentTimeMillis()}"))
        else lpuList.setValue(listOf<String>("Поликлиника 00011 ${System.currentTimeMillis()}", "Больница 10002 ${System.currentTimeMillis()}", "Лаборатория 0001 ${System.currentTimeMillis()}", "Скорая помощь 000${System.currentTimeMillis()}"))
    }

    fun updateSpecialityList() {
        if (!::specialityList.isInitialized) specialityList = MutableLiveData()
        specialityList.setValue(listOf<String>("Хирург ${System.currentTimeMillis()}", "Терапевт ${System.currentTimeMillis()}", "Уролог ${System.currentTimeMillis()}", "Кардиолог ${System.currentTimeMillis()}", "Гинеколог ${System.currentTimeMillis()}", "Окулист ${System.currentTimeMillis()}"))
    }

    fun updateDoctorList() {
        if (!::doctorList.isInitialized) doctorList = MutableLiveData()
        doctorList.setValue(listOf<String>("Иванов А. ${System.currentTimeMillis()}", "Петров П. ${System.currentTimeMillis()}", "Сидоров Г. ${System.currentTimeMillis()}", "Лифшиц Б. ${System.currentTimeMillis()}", "Хрущев Н. ${System.currentTimeMillis()}"))
    }

    fun changeUser(checkedId: Int) {
        currentUser="$checkedId"
        Storage.setCurrentUser(context, currentUser)
        currentDistrict = Storage.restore(context,"currentDistrict")
        currentName = "$checkedId === ${System.currentTimeMillis()}"//Storage.restore(context,"currentName")
        currentLPU = Storage.restore(context,"currentLPU")
        currentSpeciality = Storage.restore(context,"currentSpeciality")
        currentDoctor = Storage.restore(context,"currentDoctor")
    }

}





