package ru.mobiskif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_2.*

class Fragment2 : Fragment() {

    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        activity!!.title = mModel.cspecname.value + ' ' + mModel.cdoctorname.value

        //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) recyclerTalon.layoutManager = GridLayoutManager(this.context, 4)
        recyclerTalon.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recyclerTalon.adapter = RecylcerAdapterTalon(mModel.getTalonList().value!!, this, R.layout.card_calend)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_2, container, false)
        binding.model1 = mModel
        return binding.root
        //return inflater.inflate(R.layout.fragment_2, container, false)
    }

}
