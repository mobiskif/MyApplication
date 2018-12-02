package ru.m

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecylcerAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.hv.findViewById<TextView>(android.R.id.text1).setText(items.get(position))
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view
}
