package ru.mobiskif

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.main_activity.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mModel: MyDataModel
    val J="jop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.clpu.observe(this, Observer<Any> {
            recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            recycler3.adapter = TalonRecylcerAdapter(mModel.getTalonList().value!!, context)
            recycler3.smoothScrollBy(100, 0)
            spinner2.adapter = MySpinnerAdapter(mModel.getSpecialityList().value!!, context)
            mModel.saveUser()
        })
        mModel.cspec.observe(this, Observer<Any> {
            recycler4.layoutManager = LinearLayoutManager(this.context)
            recycler4.adapter = DoctorListAdapter(mModel.getDoctorList().value!!, nav_host_fragment, mModel )
        })
    }

    override fun onResume() {
        super.onResume()
        spinner1.adapter = MySpinnerAdapter(mModel.getLpuList().value!!, context)
        spinner1.setSelection(mModel.clpu.value!!)
        spinner1.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this
        recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recycler3.adapter = TalonRecylcerAdapter(mModel.getTalonList().value!!, context)
        recycler3.smoothScrollBy(100, 0)
        activity!!.appbar?.setExpanded(false,true)
        activity!!.collapsing_toolbar.title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent!!.id) {
            R.id.spinner1 -> mModel.clpu.value=position
            R.id.spinner2 -> mModel.cspec.value= position.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
