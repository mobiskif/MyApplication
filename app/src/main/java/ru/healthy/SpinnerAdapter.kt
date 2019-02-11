package ru.healthy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SpinnerAdapter    (private val items: MutableList<Map<String, String>>?, val context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView!=null) convertView as TextView
        else LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        if (items!=null) view.text = items[position]["Name"]
        return view
    }

    override fun getItem(position: Int): Any {
        if (items!=null) return items[position]
        else return emptyMap<String, String>()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        if (items!=null) return items.count()
        else return 0
    }

}
