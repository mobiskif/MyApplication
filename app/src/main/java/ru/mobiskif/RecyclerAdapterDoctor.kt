package ru.mobiskif

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_doctor.view.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment


class RecylcerAdapterDoctor(private val items: MutableList<Map<String, String>>, val frag: Fragment1) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textViewFIO.text = items[position].get("Name")
        holder.itemView.textViewTalons.text = "${items[position].get("CountFreeTicket")} (${items[position].get("CountFreeParticipantIE")}) тал"
        holder.itemView.textViewSpec.text = items[position].get("AriaNumber")
        holder.itemView.textViewUch.text = ""
        holder.itemView.setOnClickListener {
            Log.d("jop",""+items[position]["IdDoc"])

            frag.model.cidDoc.put("IdDoc", items[position]["IdDoc"].toString())
            frag.model.cidDoc.put("Name", items[position]["Name"].toString())
            //frag.model.setDocList()
            //Storer(frag.requireContext()).saveModel(frag.model)
            NavHostFragment.findNavController(frag).navigate(R.id.Fragment2)
        }

    }
}


