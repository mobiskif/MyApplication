package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapterCalend(private val items: List<Map<String, Any>>, private val fragm: Context, private val layout_id: Int, private var model: MainViewModel ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(fragm).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textViewFIO.text = items[position]["День недели"].toString()
        holder.itemView.text2.text = items[position]["Дата"].toString()
        holder.itemView.text3.text = items[position]["Время работы"].toString()
        holder.itemView.button.visibility=View.GONE

        (items[position]["Талоны"] as Map<String, String>).forEach { it ->
            val btn = Button(fragm)
            btn.text = it.value.subSequence(0, it.value.length)
            btn.setOnClickListener { btn ->
                model.ctalon.value = it.key
                model.ctalonvalue.value = it.value
                NavHostFragment.findNavController(model.cfragment).navigate(R.id.Fragment3)
            }
            holder.itemView.lay01.addView(btn)
        }
    }

}
