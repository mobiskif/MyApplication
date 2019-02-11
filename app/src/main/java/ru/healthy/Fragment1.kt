package ru.healthy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var model: MyViewModel
    private lateinit var binding: ru.healthy.databinding.Fragment1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(requireActivity()).get(MyViewModel::class.java)
        model.setLpulist()

    }

    override fun onResume() {
        super.onResume()

        model.lpulist.observe(activity!!, Observer { items ->
            //if (!model.from0) {
                //Log.d("jop", "lpu 1")
                spinnerLPU.adapter = SpinnerAdapter(items, requireContext())
                if (binding.spinnerLPU.adapter.count > model.pos_lpu) binding.spinnerLPU.setSelection(model.pos_lpu)
            //}
        })

        model.cidPat.observe(activity!!, Observer { items ->
            //if (model.from0) {
                //Log.d("jop", "pat 3 ${model.from0}")
                binding.invalidateAll()
                //Storer(requireContext()).saveModel(model)
                if (!model.from0 && model.cidPat.value!="") {
                    model.setHistList()
                    model.setSpecList()
                }
            //}
        })

        model.histlist.observe(activity!!, Observer { items ->
            //Log.d("jop", "hist 4")
            recyclerHist.adapter = RecylcerAdapterHistory(items, this)
        })

        model.speclist.observe(activity!!, Observer { items ->
            //Log.d("jop", "spec 5 ${model.from0}")
            spinnerSpec.adapter = SpinnerAdapterSpec(items, requireContext())
            spinnerSpec.onItemSelectedListener = this
            if (spinnerSpec.adapter.count > model.pos_spec) spinnerSpec.setSelection(model.pos_spec)
            recyclerHist.smoothScrollBy(60, 0)
        })

        model.doclist.observe(activity!!, Observer { items ->
            //Log.d("jop", "doc 6")
            recyclerDoctor.adapter = RecylcerAdapterDoctor(items, this)
        })


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        binding.recyclerHist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDoctor.layoutManager = LinearLayoutManager(context)
        binding.recyclerHist.adapter = RecylcerAdapterHistory(null, this)
        binding.recyclerDoctor.adapter = RecylcerAdapterDoctor(null, this)
        binding.spinnerLPU.adapter = SpinnerAdapter(null, requireContext())
        binding.spinnerSpec.adapter = SpinnerAdapter(null, requireContext())

        binding.spinnerLPU.onItemSelectedListener = this

        return binding.root
        //return inflater.inflate(R.layout.fragment_1, container, false);
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent!!.adapter.getItem(position) as Map<String, String>
        when (parent!!.id) {
            R.id.spinnerLPU -> {
                //Log.d("jop", "lpu 2 ${model.from0}")
                model.pos_lpu = position
                model.cidLpu = item["IdLPU"]!!.toInt()
                Storer(requireContext()).saveModel(model)
                if (!model.from0) model.setPatient()
                //model.from0=false
                //model.setSpecList()
            }
            R.id.spinnerSpec -> {
                //Log.d("jop", "spec 2")
                model.pos_spec = position
                model.cidSpec = item["IdSpesiality"]!!.toInt()
                if (!model.from0) model.setDocList()
                model.from0=false
                //model.cidDoc.put("NameSpesiality", item["NameSpesiality"]!!.toLowerCase())
                //Storer(requireContext()).saveModel(model)
                //model.setDocList()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.lpulist.removeObservers(requireActivity())
        model.cidPat.removeObservers(requireActivity())
        model.speclist.removeObservers(requireActivity())
        model.doclist.removeObservers(requireActivity())
        model.histlist.removeObservers(requireActivity())
    }

}
