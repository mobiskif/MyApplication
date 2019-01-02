package ru.m

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView


class MyListAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder2>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        //return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false))
        return ViewHolder2(LayoutInflater.from(context).inflate(android.R.layout.activity_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        var tv = holder.hv
        tv.setText(items.get(position))

        tv.setOnClickListener {
            tv.text = "=-= $position"

            val mModel = context?.run { ViewModelProviders.of(context as FragmentActivity).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
            //mModel.updateLpuList()
        }
    }

}

class ViewHolder2 (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view.findViewById<TextView>(android.R.id.text1)
}


