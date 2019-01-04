package ru.mobiskif

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyRecylcerAdapter(val items: List<String>, private val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.talon_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = holder.hv
        tv.setText(items[position])

        tv.setOnClickListener {
            tv.text = "=-= $position"
            //val mModel = context?.run { ViewModelProviders.of(context as FragmentActivity).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
            //mModel.updateLpuList()
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view.findViewById<TextView>(R.id.text1)
}

