package ru.m

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*

class BlankFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model = ViewModelProviders.of(this).get(myViewModel::class.java)
        val authors = listOf<String>("Conan Doyle, Arthur", "Christie, Agatha", "Collins, Wilkie");
        val adapter = ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, authors);


        var users: MutableLiveData<List<String>> = model.getUsers()
        users.observe(this, Observer<List<String>>{ users ->
            //plant_detail.setText(users.get(0))
            //listview?.adapter=adapter
            //spinner?.adapter=adapter
            recycler.layoutManager = LinearLayoutManager(this.context)
            recycler.adapter=MyAdapter(authors, this.context)
        })

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false)
    }
}

class MyAdapter(val items: List<String>, val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.hv.findViewById<TextView>(android.R.id.text1).setText(items.get(position))
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val hv = view
}
