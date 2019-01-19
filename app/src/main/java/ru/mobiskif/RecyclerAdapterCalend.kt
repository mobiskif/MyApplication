package ru.mobiskif

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapterCalend(private val items: List<Map<String, Any>>, private val fragm: Fragment, private val layout_id: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(fragm.context).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position]["День недели"].toString()
        holder.itemView.text2.text = items[position]["Дата"].toString()
        holder.itemView.text3.text = items[position]["Время работы"].toString()

        (items[position]["Талоны"] as Map<String, String>).forEach { it ->
            val btn = Button(fragm.context)
            btn.setText(it.value.subSequence(0, it.value.length))
            btn.setOnClickListener { it ->
                NavHostFragment.findNavController(fragm).navigate(R.id.Fragment3)
            }
            holder.itemView.lay01.addView(btn)
        }
    }

}
