package ru.healthy

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

public class Storer(val context: Context) {

    fun saveModel(model: MyViewModel) {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        val eddef = defsettings.edit()
        eddef.putInt("pos_user", model.pos_user)
        eddef.apply()

        istore(model.pos_user, "pos_distr", model.pos_distr)
        istore(model.pos_user, "pos_lpu", model.pos_lpu)
        istore(model.pos_user, "pos_spec", model.pos_spec)
        istore(model.pos_user, "cidLpu", model.cidLpu)
        istore(model.pos_user, "cidSpec", model.cidSpec)
        sstore(model.pos_user, "cidPat", model.cidPat.value!!)
        sstore(model.pos_user, "cname", model.cname.value!!)
        sstore(model.pos_user, "cfam", model.cfam.value!!)
        sstore(model.pos_user, "cotch", model.cotch.value!!)
        sstore(model.pos_user, "cdate", model.cdate.value!!)
    }

    fun loadModel(model: MyViewModel, pos_user: Int) {
        model.cname.value = srestore(pos_user, "cname")
        model.cfam.value = srestore(pos_user, "cfam")
        model.cotch.value = srestore(pos_user, "cotch")
        model.cdate.value = srestore(pos_user, "cdate")
        model.cidPat.value = srestore(pos_user, "cidPat")
        model.cidLpu = irestore(pos_user, "cidLpu")
        model.cidSpec = irestore(pos_user, "cidSpec")
        model.pos_spec = irestore(pos_user, "pos_spec")
        model.pos_lpu = irestore(pos_user, "pos_lpu")
        model.pos_distr = irestore(pos_user, "pos_distr")
        model.pos_user = pos_user
    }

    fun restoreuser(): Int {
        val defsettings = PreferenceManager.getDefaultSharedPreferences(context)
        return defsettings.getInt("pos_user", 1)
    }

    private fun irestore(id: Int, key: String): Int {
        val settings = context.getSharedPreferences(id.toString(), 0)
        return settings.getInt(key, 1)
    }

    private fun srestore(id: Int, key: String): String {
        val settings = context.getSharedPreferences(id.toString(), 0)
        return settings.getString(key, "")
    }

    private fun istore(id: Int, key: String, value: Int) {
        val settings = context.getSharedPreferences(id.toString(), 0)
        val ed = settings.edit()
        ed.putInt(key, value)
        ed.apply()
    }

    private fun sstore(id: Int, key: String, value: String) {
        val settings = context.getSharedPreferences(id.toString(), 0)
        val ed = settings.edit()
        ed.putString(key, value)
        ed.apply()
    }

    fun showModel(model: MyViewModel) {
        Log.d("jop", "model=\n" +
                "pos_user= ${model.pos_user}\n"+
                "pos_distr= ${model.pos_distr}\n"+
                "pos_lpu= ${model.pos_lpu}\n"+
                "pos_spec= ${model.pos_spec}\n"+
                "cidLpu= ${model.cidLpu}\n"+
                "cidSpec= ${model.cidSpec}\n"
        )
    }


}