package ru.m

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView


class RecylcerAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tv = holder.hv.findViewById<TextView>(R.id.text1)
        tv.setText(items.get(position))
        tv.setOnClickListener {
            Log.d("jop","click $position click ${items.get(position)}")
            tv.setText("====================")
            notifyItemChanged(position)
            notifyDataSetChanged()

            val mModel = context?.run {
                ViewModelProviders.of(context as FragmentActivity).get(RecyclerViewModel::class.java)
            } ?: throw Exception("Invalid Activity")

            //val mModel = ViewModelProviders.of(context as FragmentActivity).get(RecyclerViewModel::class.java)
            mModel.update2()
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view
}
