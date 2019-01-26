package ru.mobiskif

import android.content.res.Configuration
import android.os.Bundle
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

        model.getLpulist().observe(activity!!, Observer<List<String>> { lpus ->
            spinnerLPU!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lpus)
            spinnerLPU!!.setSelection(model.pos_lpu)
            spinnerLPU!!.onItemSelectedListener = this
        })

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recyclerDoctor.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        model.getHistoryList().observe(activity!!, Observer { hist ->
            recyclerHistory!!.adapter = RecylcerAdapterHistory(model.getHistoryList().value!!, context!!, R.layout.card_history, model)
            recyclerHistory.smoothScrollBy(80, 0)
        })

        model.getSpecList().observe(activity!!, Observer<List<String>> { specs ->
            spinnerSpec!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specs)
            spinnerSpec!!.onItemSelectedListener = this
        })

        recyclerDoctor.layoutManager = LinearLayoutManager(context)
        model.getDoctorList().observe(activity!!, Observer { hist ->
            recyclerDoctor.adapter = RecylcerAdapterDoctor(model.getDoctorList())
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.spinnerLPU -> {
                if (model.pos_lpu != position) {
                    model.pos_lpu = position
                    Storage(context!!).saveModel(model)
                    model.updateSpecList()
                }
            }
            R.id.spinnerSpec -> {
                //if (model.pos_spec != position) {
                model.pos_spec = position
                Storage(context!!).saveModel(model)
                model.updateDocList()
                //}
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.getLpulist().removeObservers(activity!!)
        model.getHistoryList().removeObservers(activity!!)
        model.getSpecList().removeObservers(activity!!)
        model.getDoctorList().removeObservers(activity!!)
    }

}
