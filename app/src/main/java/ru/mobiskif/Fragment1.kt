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
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()
        activity!!.title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value

        spinnerLPU.onItemSelectedListener = this
        spinnerLPU.adapter = mModel.lpuAdapter
        spinnerLPU.setSelection(mModel.clpu.value!!)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recycler3.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recyclerHistory.adapter = RecylcerAdapterHistory(mModel.getTalonList().value!!, this, R.layout.card_talon)
        recyclerHistory.smoothScrollBy(80, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent!!.id) {
            R.id.spinnerLPU -> {
                Log.d("jop", "Сработал onItemSelected spinner1($position)")
                mModel.clpu.value = position
                mModel.clpuname.value = parent.adapter.getItem(position).toString()

                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recycler3.layoutManager = GridLayoutManager(this.context, 2)
                else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                recyclerHistory.adapter = RecylcerAdapterHistory(mModel.getTalonList().value!!, this, R.layout.card_talon)
                recyclerHistory.smoothScrollBy(80, 0)

                spinnerSpec.adapter = SpinnerAdapter(mModel.getSpecialityList().value!!, context)
                spinnerSpec.setSelection(mModel.cspec.value!!)
                spinnerSpec.onItemSelectedListener = this
            }
            R.id.spinnerSpec -> {
                Log.d("jop", "Сработал onItemSelected spinner2($position)")
                mModel.cspec.value = position
                mModel.cspecname.value = parent.adapter.getItem(position).toString()
                recyclerDoctor.layoutManager = LinearLayoutManager(this.context)
                recyclerDoctor.adapter = RecylcerAdapter(mModel.getDoctorList().value!!, this, R.layout.card_doctor, mModel)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
