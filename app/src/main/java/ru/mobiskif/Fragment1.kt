package ru.mobiskif

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_0.*
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recycler3.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        //recyclerHistory.adapter = model.adapterHistory
        //recyclerHistory.smoothScrollBy(80, 0)

        recyclerDoctor.layoutManager = LinearLayoutManager(this.context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        model.getLpulist().observe(activity!!, Observer<List<String>> { lpus ->
            binding.spinnerLPU!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lpus)
            binding.spinnerLPU!!.setSelection(model.pos_lpu)
            binding.spinnerLPU!!.onItemSelectedListener = this
        })
        return binding.root
        //return inflater.inflate(R.layout.fragment_0, container, false)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent!!.id) {
            R.id.spinnerLPU -> {
                /*
                Log.d("jop", "Сработал onItemSelected spinnerLPU($position)")
                mModel.clpu.value = position
                mModel.clpuname.value = parent.adapter.getItem(position).toString()

                recyclerHistory.adapter = RecylcerAdapterHistory(model.getHistory().value!!, context!!, R.layout.card_history, mModel)
                recyclerHistory.smoothScrollBy(80, 0)

                spinnerSpec.adapter = SpinnerAdapter(mModel.getSpecialityList().value!!, context!!)
                spinnerSpec.onItemSelectedListener = this
                spinnerSpec.setSelection(mModel.cspec.value!!)
                */
            }
            R.id.spinnerSpec -> {
                /*
                Log.d("jop", "Сработал onItemSelected spinnerSpec($position)")
                mModel.cspec.value = position
                mModel.cspecname.value = parent.adapter.getItem(position).toString()
                recyclerDoctor.adapter = RecylcerAdapterDoctor(mModel.getDoctorList().value!!, context!!, R.layout.card_doctor, mModel)
                */
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
