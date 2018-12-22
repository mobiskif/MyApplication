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


class MyRecylcerAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tv = holder.hv
        tv.setText(items.get(position))

        tv.setOnClickListener {
            tv.text = "=-= $position"

            val mModel = context?.run { ViewModelProviders.of(context as FragmentActivity).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
            //mModel.updateLpuList()
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view.findViewById<TextView>(R.id.text1)
}

