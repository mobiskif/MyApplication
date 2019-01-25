package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_calend.view.*

class RecylcerAdapterHistory(private val items: List<Map<String, Any>>, private val context: Context, private val layout_id: Int, model: MyViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = SimpleViewHolder(LayoutInflater.from(context).inflate(layout_id, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text1.text = items[position]["День недели"].toString()
    }

}
