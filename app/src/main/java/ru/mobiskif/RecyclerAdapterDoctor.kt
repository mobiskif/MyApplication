package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_doctor.view.*

class RecylcerAdapterDoctor(private val items: List<String>, private val fragm: Context, private val layout_id: Int, private var model: MainViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(fragm).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position]
        holder.itemView.setOnClickListener {
            //Toast.makeText(fragm, "Это RecyclerAdapter $position = ${R.layout.card_doctor}", Toast.LENGTH_SHORT).show()
            model.cdoctor.value = position
            model.cdoctorname.value = items[position]
            NavHostFragment.findNavController(model.cfragment!!).navigate(R.id.Fragment2)
        }

        //holder.itemView.text1.setOnClickListener {holder.itemView.text1.text = "== $position"}
    }
}
