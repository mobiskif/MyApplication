package ru.m

import android.R
import android.content.Context
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter.IGNORE_ITEM_VIEW_TYPE
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

class SpinnerAdapter(val items: List<String>, val context: Context?) : SpinnerAdapter {
    override fun getItemViewType(position: Int): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return IGNORE_ITEM_VIEW_TYPE
    }

    override fun getItemId(position: Int): Long {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun isEmpty(): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.isEmpty()
    }


    private var obs: DataSetObserver? = null

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        obs = observer
    }

    override fun getViewTypeCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return 1
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
    }

    override fun getItem(position: Int): Any {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.get(position)
    }

    override fun getCount(): Int {
        return items.count()
    }


    /*
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
    */

}
