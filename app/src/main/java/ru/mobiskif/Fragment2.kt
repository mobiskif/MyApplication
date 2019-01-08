package ru.mobiskif

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_2.*

class Fragment2 : Fragment() {

    private lateinit var mModel: MainViewModel
    val J="jop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) recyclerTalon.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerTalon.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        recyclerTalon.adapter = RecylcerAdapter(mModel.getTalonList().value!!, this, R.layout.card_talon, mModel)
        activity!!.title = mModel.cspecname.value + ' ' + mModel.cdoctorname.value
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_2, container, false)
        binding.model1 = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

}
