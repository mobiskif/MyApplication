package ru.mobiskif

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import com.google.android.material.appbar.AppBarLayout



class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mModel: MyDataModel
    val J="jop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.clpu.observe(this, Observer<Any> {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) recycler3.layoutManager = GridLayoutManager(this.context, 2)
            else recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            recycler3.adapter = TalonRecylcerAdapter(mModel.getTalonList().value!!, context)
            recycler3.smoothScrollBy(80, 0)
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
        Log.d(J, getResources().getConfiguration().orientation.toString())
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            recycler3.layoutManager = GridLayoutManager(this.context, 2)
        }
        else recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        //recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recycler3.adapter = TalonRecylcerAdapter(mModel.getTalonList().value!!, context)
        recycler3.smoothScrollBy(80, 0)
        //activity!!.appbar?.setExpanded(false,true)
        //var par = activity!!.collapsing_toolbar.layoutParams as AppBarLayout.LayoutParams
        //par.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED)
        //ViewCompat.setNestedScrollingEnabled(drawer_layout, false);
        //activity?.actionBar!!.title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value
        activity!!.title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value
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
