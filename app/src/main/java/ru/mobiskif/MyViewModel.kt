package ru.mobiskif

import androidx.lifecycle.*

class MyViewModel : ViewModel() {

    var pos_user = 1
    var pos_distr = 1
    var pos_lpu = 1
    var pos_spec = 1

    val distrlist = MutableLiveData<MutableList<Map<String, String>>>()
    val lpulist = MutableLiveData<MutableList<Map<String, String>>>()
    val speclist = MutableLiveData<MutableList<Map<String, String>>>()
    val doclist = MutableLiveData<MutableList<Map<String, String>>>()
    val histlist = MutableLiveData<MutableList<Map<String, String>>>()
    val talonlist = MutableLiveData<MutableList<Map<String, String>>>()

    private lateinit var cuser: MutableLiveData<Int>

    var cname = MutableLiveData<String>()
    var cfam = MutableLiveData<String>()
    var cotch = MutableLiveData<String>()
    var cdate = MutableLiveData<String>("")
    val cidPat = MutableLiveData<String>()
    val cerror = MutableLiveData<String>()
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

    fun setHistList() {
        val args = arrayOf(cidLpu, cidPat.value)
        Thread({ histlist.postValue(Hub().GetHist("GetPatientHistory", args)) }).start()
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

    fun setAppointment(cidTalon: MutableMap<String, String>) {
        val args = arrayOf(cidLpu, cidTalon["IdAppointment"], cidPat.value)
        Thread({ cerror.postValue(Hub().SetApp("SetAppointment", args)["Success"]) }).start()
    }

    fun refAppointment(cidTalon: MutableMap<String, String>) {
        val args = arrayOf(cidLpu, cidTalon["IdAppointment"], cidPat.value)
        Thread({ cerror.postValue(Hub().RefApp("CreateClaimForRefusal", args)["Success"]) }).start()
    }

}


