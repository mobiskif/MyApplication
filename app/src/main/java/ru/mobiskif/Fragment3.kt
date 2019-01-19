package ru.mobiskif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class Fragment3 : Fragment() {

    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        mModel.cfragment=this
        //activity!!.title = mModel.cspecname.value + ' ' + mModel.cdoctorname.value
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment3Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_3, container, false)
        binding.model3 = mModel
        return binding.root
        //return inflater.inflate(R.layout.fragment_2, container, false)
    }

}
