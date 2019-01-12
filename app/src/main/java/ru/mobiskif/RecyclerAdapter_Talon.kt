package ru.mobiskif

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapter_Talon(val items: List<Map<String, Any>>, private val fragm: Fragment, val layout_id: Int, var model: MainViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(fragm.context).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = items[position]
        //item.get()

        holder.itemView.text1.text = items[position].get("День недели").toString()
        holder.itemView.text2.text = items[position].get("Дата").toString()
        holder.itemView.text3.text = items[position].get("Время работы").toString()
        //holder.itemView.text2.text = "$position"

        holder.itemView.setOnClickListener {
            //Toast.makeText(fragm.context, "Это RecyclerAdapter $position", Toast.LENGTH_SHORT).show()
            model.cdoctor.value = position
            //model.cdoctorname.value = items[position]
            if (layout_id==R.layout.card_doctor) NavHostFragment.findNavController(fragm).navigate(R.id.Fragment2)
            if (layout_id==R.layout.card_talon) NavHostFragment.findNavController(fragm).navigate(R.id.action_Fragment2_to_help)
        }

        //holder.itemView.text1.setOnClickListener {holder.itemView.text1.text = "== $position"}
    }


}
