package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*

class BlankFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model = ViewModelProviders.of(this).get(myViewModel::class.java)
        val authors = arrayOf("Conan Doyle, Arthur", "Christie, Agatha", "Collins, Wilkie");
        val adapter = ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, authors);


        var users: MutableLiveData<List<String>> = model.getUsers()
        users.observe(this, Observer<List<String>>{ users ->
            plant_detail.setText(users.get(0))
            //listview?.adapter=adapter
            //spinner?.adapter=adapter
        })

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false)
    }
}
