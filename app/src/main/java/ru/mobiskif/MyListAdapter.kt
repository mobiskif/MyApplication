package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder2>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val tv = holder.hv
        tv.setText(items[position])
        tv.setOnClickListener {
            tv.text = "=-= $position"
        }
    }
}

class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
    val hv = view.findViewById<TextView>(R.id.text1)
}


