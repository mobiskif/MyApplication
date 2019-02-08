package ru.healthy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_talon.view.*

class RecylcerAdapterHistory(private var items: MutableList<Map<String, String>>, val frag: Fragment1) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun getHistoryList(): MutableList<Map<String, String>> {
        var talonlist = mutableListOf<Map<String, String>>()
        val ll = mutableListOf<Map<String, String>>()
        var tal = mutableListOf<Map<String, String>>()
        var m: MutableMap<String, String> = mutableMapOf()
        var mt: MutableMap<String, String> = mutableMapOf()


        m = mutableMapOf()
        m["VisitStart"] = "Пн"
        m["VisitEnd"] = "28/12/19"
        m["IdAppointment"] = "14:30 - 18:45"

        mt = mutableMapOf()
        mt["1234-1"] = "14:56"
        mt["1234-2"] = "15:56"
        //m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["VisitStart"] = "Ср"
        m["VisitEnd"] = "30/12/19"
        m["IdAppointment"] = "17:00 - 18:45"
        mt = mutableMapOf()
        mt["123456-4"] = "17:56"
        //m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["VisitStart"] = "СБ"
        m["VisitEnd"] = "30/12/19"
        m["IdAppointment"] = "17:00 - 18:45"
        mt = mutableMapOf()
        mt["123456-4"] = "17:56"
        //m["Талоны"] = mt
        ll.add(m)

        m = mutableMapOf()
        m["VisitStart"] = "Вс"
        m["VisitEnd"] = "30/12/19"
        m["IdAppointment"] = "17:00 - 18:45"
        mt = mutableMapOf()
        mt["123456-4"] = "17:56"
        //m["Талоны"] = mt
        ll.add(m)

        return ll
    }

    init {
        //items = getHistoryList()
    }

    override fun getItemCount(): Int {
        return items!!.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_talon, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position].get("VisitStart")
        holder.itemView.text2.text = items[position]["VisitEnd"]
        holder.itemView.text3.text = items[position].get("NameSpesiality")
        holder.itemView.setOnClickListener {
            frag.model.cidTalon.put("IdAppointment", items[position]["IdAppointment"].toString())
            frag.model.cidTalon.put("VisitStart", items[position]["VisitStart"].toString())
            frag.model.cidTalon.put("VisitEnd", items[position]["VisitEnd"].toString())
            frag.model.cidTalon.put("NameSpesiality", items[position]["NameSpesiality"].toString())
            NavHostFragment.findNavController(frag).navigate(R.id.Fragment4)
        }
    }

}


