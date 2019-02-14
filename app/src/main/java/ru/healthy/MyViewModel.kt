package ru.healthy

import androidx.lifecycle.*

class MyViewModel : ViewModel() {

    var pos_user = 1
    var pos_distr = 0
    var pos_lpu = 0
    var pos_spec = 0

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
    val cidPat = MutableLiveData<String>("")
    val cidPatError = MutableLiveData<String>("")
    val cerror = MutableLiveData<String>()
    var cidDoc = mutableMapOf<String, String>()
    var cidSpeciality = mutableMapOf<String, String>()
    var cidTalon = mutableMapOf<String, String>()
    var cidLpu = 0
    var cidSpec = 23
    //var cidPat =""
    var from0 = false

    fun setDistrlist() = Thread({ distrlist.postValue(Hub().GetDistr("GetDistrictList")) }).start()
    fun setLpulist() = Thread({ lpulist.postValue(Hub().GetLpu("GetLPUList", pos_distr)) }).start()
    fun setSpecList() = Thread({
        //if (cidPat.value==null) cidPat.postValue("22")
        val args = arrayOf(cidLpu, cidPat)
        speclist.postValue(Hub().GetSpec("GetSpesialityList", args))
    }).start()

    fun setPatient() {
        val args = arrayOf(cname.value, cfam.value, cotch.value, cdate.value)
        Thread ({
            val pat = Hub().GetPat("CheckPatient", cidLpu, args)
            cidPat.postValue(pat["IdPat"])
            cidPatError.postValue(pat["ErrorDescription"])
        }).start()
        //Thread({ cidPat.postValue(Hub().GetPat("CheckPatient", cidLpu, args)["IdPat"]) }).start()
        //Thread({ cidPatError.postValue(Hub().GetPat("CheckPatient", cidLpu, args)["ErrorDescription"]) }).start()
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


