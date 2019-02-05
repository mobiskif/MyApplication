package ru.mobiskif

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_talon.view.*


class RecylcerAdapterTalons(private val items: MutableList<Map<String, String>>, val frag: Fragment2) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_talon, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position].get("VisitStart")
        holder.itemView.text2.text = items[position]["VisitEnd"]
        holder.itemView.text3.text = ""//items[position].get("IdAppointment")
        holder.itemView.setOnClickListener {
            Log.d("jop",""+items[position]["IdAppointment"])
            frag.model.cidTalon.put("IdAppointment", items[position]["IdAppointment"].toString())
            frag.model.cidTalon.put("VisitStart", items[position]["VisitStart"].toString())
            frag.model.cidTalon.put("VisitEnd", items[position]["VisitEnd"].toString())
            NavHostFragment.findNavController(frag).navigate(R.id.Fragment3)
        }

    }
}


