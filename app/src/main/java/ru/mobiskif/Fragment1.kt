package ru.mobiskif

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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        model.setLpulist()
    }

    override fun onResume() {
        super.onResume()
        recyclerDoctor.layoutManager = LinearLayoutManager(context)

        model.lpulist.observe(activity!!, Observer { items ->
            spinnerLPU.adapter=SpinnerAdapter(items, requireContext())
            spinnerLPU.onItemSelectedListener=this
            if (spinnerLPU.adapter.count > model.pos_lpu) spinnerLPU.setSelection(model.pos_lpu)
        })

        model.cidPat.observe(activity!!, Observer { items ->
            binding.invalidateAll()
            model.setSpecList()
        })

        model.speclist.observe(activity!!, Observer { items ->
            spinnerSpec.adapter=SpinnerAdapterSpec(items, requireContext())
            spinnerSpec.onItemSelectedListener=this
            if (spinnerSpec.adapter.count > model.pos_spec) spinnerSpec.setSelection(model.pos_spec)
            //model.setDocList()
        })

        model.doclist.observe(activity!!, Observer { items ->
            recyclerDoctor.adapter=RecylcerAdapterDoctor(items, this)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        return binding.root
        //return inflater.inflate(R.layout.fragment_1, container, false);
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent!!.adapter.getItem(position) as Map<String, String>
        when (parent!!.id) {
            R.id.spinnerLPU -> {
                model.pos_lpu = position
                if (model.cidLpu!=item["IdLPU"]!!.toInt()) {
                    model.cidLpu = item["IdLPU"]!!.toInt()
                    model.setPatient()
                    Storer(requireContext()).saveModel(model)
                }
            }
            R.id.spinnerSpec -> {
                model.pos_spec = position
                //if (model.cidSpec!=item["IdSpesiality"]!!.toInt()) {
                    model.cidSpec = item["IdSpesiality"]!!.toInt()
                    model.cidDoc.put("NameSpesiality", item["NameSpesiality"]!!.toLowerCase() )
                    model.setDocList()
                    Storer(requireContext()).saveModel(model)
                //}
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.lpulist.removeObservers(activity!!)
        model.cidPat.removeObservers(activity!!)
        model.speclist.removeObservers(activity!!)
        model.doclist.removeObservers(activity!!)
    }

}
