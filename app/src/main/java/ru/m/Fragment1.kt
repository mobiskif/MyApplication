package ru.m

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment() {


    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        spinner.adapter = MySpinnerAdapter(mModel.getLpuList().value!!, context)
        spinner2.adapter = MySpinnerAdapter(mModel.getSpecialityList().value!!, context)
        //spinner2.setSelection(mModel.getLPU().value!!)
        recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recycler3.adapter = MyRecylcerAdapter(mModel.getDoctorList().value!!, context)

        recycler4.layoutManager = LinearLayoutManager(this.context)
        recycler4.adapter = MyListAdapter(mModel.getDoctorList().value!!, context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

}
