package ru.mobiskif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.doctor_card.view.*
import android.widget.Toast



class DoctorListAdapter(private val items: List<String>, private val navfragment: Fragment, val mModel: MyDataModel) : RecyclerView.Adapter<ViewHolder2>() {
    //lateinit var itemClickListener: AdapterView.OnItemClickListener

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val view = LayoutInflater.from(navfragment.context).inflate(R.layout.doctor_card, parent, false)
        return ViewHolder2(view, items)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val tv = holder.hv
        var cv = holder.cv
        tv.text = items[position]
        cv.textView6.text = items[position]
    }
}

class ViewHolder2(view: View, private val items: List<String>) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val hv = view.findViewById<TextView>(R.id.text1)!!
    val cv = view.findViewById<CardView>(R.id.card_view)!!
    init { itemView.setOnClickListener(this) }
    override fun onClick(view: View) {
        val pos = adapterPosition
        if (pos != RecyclerView.NO_POSITION) {
            val clickedDataItem = items[pos]
            Toast.makeText(view.context, "You clicked $clickedDataItem", Toast.LENGTH_SHORT).show()
        }
    }
}


