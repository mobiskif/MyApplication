package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_doctor.view.*

class RecylcerAdapterDoctor(private val items: List<String>, private val fragm: Context, private val layout_id: Int, private var model: MyViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(fragm).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(fragm, "Это RecyclerAdapter $position = ${R.layout.card_doctor}", Toast.LENGTH_SHORT).show()
            //NavHostFragment.findNavController(model.).navigate(R.id.Fragment2)
        }
    }
}
