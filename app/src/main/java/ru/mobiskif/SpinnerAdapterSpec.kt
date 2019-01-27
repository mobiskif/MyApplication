package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SpinnerAdapterSpec(private val items: MutableList<Map<String, String>>, val context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView!=null) convertView as TextView
        else LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        view.text = items[position]["NameSpesiality"] + " (" + items[position]["CountFreeParticipantIE"] + ")"
/*
        val view = if (convertView!=null) convertView as LinearLayout
        else LayoutInflater.from(context).inflate(android.R.layout.two_line_list_item, parent, false) as LinearLayout
        view.findViewById<TextView>(android.R.id.text1).text = items[position]["LPUShortName"]
        view.findViewById<TextView>(android.R.id.text2).text = items[position]["IdLPU"]
*/
        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.count()
    }

}
