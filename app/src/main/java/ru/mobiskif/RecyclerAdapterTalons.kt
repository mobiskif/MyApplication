package ru.mobiskif

import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.itemView.text2.text = "${items[position]["VisitEnd"]}"
        holder.itemView.text3.text = items[position].get("IdAppointment")
        holder.itemView.setOnClickListener {
            //Log.d("jop",""+items[position]["IdDoc"])
            //frag.model.cidDoc.put("IdDoc", items[position]["IdDoc"].toString())
            //frag.model.cidDoc.put("Name", items[position]["Name"].toString())
            //frag.model.setDocList()
            //Storer(frag.requireContext()).saveModel(frag.model)
            //NavHostFragment.findNavController(frag).navigate(R.id.Fragment3)
        }

    }
}


