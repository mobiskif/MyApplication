package ru.healthy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_doctor.view.*

class RecylcerAdapterDoctor(private val items: MutableList<Map<String, String>>?, val frag: Fragment1) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        if (items!=null) return items.count()
        else return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items!=null) {
            holder.itemView.textViewFIO.text = items[position].get("Name")
            holder.itemView.textViewTalons.text = "${items[position].get("CountFreeParticipantIE")}  тал" //(${items[position].get("CountFreeTicket")})
            holder.itemView.textViewSpec.text = items[position].get("AriaNumber")
            holder.itemView.textViewUch.text = ""
            holder.itemView.setOnClickListener {
                frag.model.cidDoc.put("IdDoc", items[position]["IdDoc"].toString())
                frag.model.cidDoc.put("Name", items[position]["Name"].toString())
                NavHostFragment.findNavController(frag).navigate(R.id.Fragment2)
            }
        }
    }
}


