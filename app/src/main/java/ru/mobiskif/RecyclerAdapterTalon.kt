package ru.mobiskif

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapterTalon(private val items: List<Map<String, Any>>, private val fragm: Fragment, private val layout_id: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(fragm.context).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position]["День недели"].toString()
        holder.itemView.text2.text = items[position]["Дата"].toString()
        holder.itemView.text3.text = items[position]["Время работы"].toString()

        val mm = items[position]["Талоны"] as Map<String, String>

        holder.itemView.text01.text = "Талоны:"
        mm.forEach { it ->
            val tv = TextView(fragm.context)
            tv.layoutParams = holder.itemView.text01.layoutParams
            tv.text = it.value
            tv.setTag(R.id.tag_accessibility_actions, it.key)
            tv.setOnClickListener { it ->
                Toast.makeText(fragm.context, "Это Текст ${it.getTag(R.id.tag_accessibility_actions)}", Toast.LENGTH_SHORT).show()
                if (layout_id == R.layout.card_doctor) NavHostFragment.findNavController(fragm).navigate(R.id.Fragment2)
                if (layout_id == R.layout.card_calend) NavHostFragment.findNavController(fragm).navigate(R.id.action_Fragment2_to_help)
            }
            holder.itemView.lay01.addView(tv)
        }
    }

}
