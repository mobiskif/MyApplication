package ru.mobiskif

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapterHistory() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //var talonlist: MutableLiveData<List<Map<String, Any>>>
    val items = getHistoryList().value

    fun getHistoryList(): MutableLiveData<List<Map<String, Any>>> {
        var talonlist = MutableLiveData<List<Map<String, Any>>>()
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

    override fun getItemCount(): Int {
        return items!!.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_calend, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textViewFIO.text = items!![position]["День недели"].toString()
    }

}
