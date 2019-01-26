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
import androidx.lifecycle.MutableLiveData


class RecylcerAdapterDoctor(private val items: MutableLiveData<MutableList<Map<String, String>>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.value!!.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textViewFIO.text = items.value!![position].get("Name")
        holder.itemView.textViewTalons.text = "${items.value!![position].get("CountFreeTicket")} (${items.value!![position].get("CountFreeParticipantIE")}) тал"
        holder.itemView.textViewSpec.text = items.value!![position].get("Snils")
        holder.itemView.textViewUch.text = items.value!![position].get("AriaNumber")
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Это RecyclerAdapter $position = ${R.layout.card_doctor}", Toast.LENGTH_SHORT).show()
            //NavHostFragment.findNavController(model.).navigate(R.id.Fragment2)
        }
    }
}


