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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        Log.d("jop", "\n after load:\n" +
                "pos_user= ${model.pos_user}\n"+
                "pos_distr= ${model.pos_distr}\n"+
                "pos_lpu= ${model.pos_lpu}\n"+
                "pos_spec= ${model.pos_spec}\n"+
                "cidLpu= ${model.cidLpu}\n"+
                "cidSpec= ${model.cidSpec}\n"

        )
    }

    override fun onResume() {
        super.onResume()

        model.getLpulist().observe(activity!!, Observer { lpus ->
            //lpus.toTypedArray()
            //spinnerLPU!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lpus)
            spinnerLPU!!.adapter = SpinnerAdapter(lpus, this.requireContext())
            spinnerLPU!!.setSelection(model.pos_lpu)
            spinnerLPU!!.onItemSelectedListener = this
        })
        model.getPatient().observe(activity!!, Observer { pat ->
            //Log.d("jop", pat["IdPat"])
            activity!!.title = "Pat: "+pat["IdPat"]
            //model.pos_spec = 0
            model.updateSpecList()
        })

/*
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recyclerDoctor.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        model.getHistoryList().observe(activity!!, Observer { hist ->
            //recyclerHistory!!.adapter = RecylcerAdapterHistory(model.getHistoryList().value!!, context!!, R.layout.card_history, model)
            //recyclerHistory.smoothScrollBy(80, 0)
        })
*/
        model.getSpecList().observe(activity!!, Observer { specs ->
            //spinnerSpec!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specs)
            spinnerSpec!!.adapter = SpinnerAdapterSpec(specs, this.requireContext())
            //spinnerSpec!!.setSelection(model.pos_spec)
            spinnerSpec!!.onItemSelectedListener = this
            //model.updateDocList()
            model.cidSpec = specs[0]["IdSpesiality"]!!.toInt()
            model.updateDocList()

        })

        recyclerDoctor.layoutManager = LinearLayoutManager(context)
        model.getDocList().observe(activity!!, Observer { docs ->
            //recyclerDoctor.adapter = RecylcerAdapterDoctor(model.getDocList())
            recyclerDoctor.adapter = RecylcerAdapterDoctor(docs)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent!!.adapter.getItem(position) as Map<String,String>
        when (parent!!.id) {
            R.id.spinnerLPU -> {
                if (model.pos_lpu != position) {
                    model.pos_lpu = position
                    model.cidLpu = item["IdLPU"]!!.toInt()
                    model.updatePatient()
                }
            }
            R.id.spinnerSpec -> {
                if (model.pos_spec != position) {
                    model.pos_spec = position
                    model.cidSpec = item["IdSpesiality"]!!.toInt()
                    model.updateDocList()
                }
            }
        }
        Storage(context!!).saveModel(model)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.getLpulist().removeObservers(activity!!)
        model.getPatient().removeObservers(activity!!)
        //model.getHistoryList().removeObservers(activity!!)
        model.getSpecList().removeObservers(activity!!)
        model.getDocList().removeObservers(activity!!)
    }

}
