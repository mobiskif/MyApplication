package ru.mobiskif

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class DoctorListAdapter(private val items: List<String>, private val context: Context?, private val navfragment: Fragment) : RecyclerView.Adapter<ViewHolder2>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(context).inflate(R.layout.doctor_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val tv = holder.hv
        var cv = holder.cv
        tv.text = items[position]
        cv.setOnClickListener { NavHostFragment.findNavController(navfragment).navigate(R.id.Fragment2) }
    }
}

class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
    val hv = view.findViewById<TextView>(R.id.text1)!!
    val cv = view.findViewById<CardView>(R.id.card_view)!!
}


