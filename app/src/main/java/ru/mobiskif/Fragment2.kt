package ru.mobiskif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_2.*
import kotlinx.android.synthetic.main.main_activity.*

class Fragment2 : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mModel: MyDataModel
    val J="jop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        recyclerTalon.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        recyclerTalon.adapter = TalonListAdapter(mModel.getTalonList().value!!, context, nav_host_fragment )
        activity!!.collapsing_toolbar.title = mModel.cspec.value + ' ' + mModel.cfam.value
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_2, container, false)
        binding.model1 = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
