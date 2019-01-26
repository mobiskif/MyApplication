package ru.mobiskif

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SpinnerAdapterCursor(private val items: Cursor, val context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView!=null) convertView as TextView
        else LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false) as TextView
        view.text = items.position.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return items.position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.count
    }

}
