package ru.mobiskif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class Fragment1_old : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        //spinnerLPU!!.adapter //= ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, context!!.resources.getStringArray(R.array.area).toMutableList())
/*
        model.setLpulist().observe(activity!!, Observer { lpus ->
            //spinnerLPU!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lpus)
            spinnerLPU.adapter = SpinnerAdapter(lpus, requireContext())
            spinnerLPU.onItemSelectedListener = this
            spinnerLPU.setSelection(model.pos_lpu)
        })

        model.cidPat.observe(activity!!, Observer { pat ->
            binding.invalidateAll()
            //spinnerSpec.adapter //= SpinnerAdapterSpec(model.getSpecList().value!!, requireContext())
        })

        var a = model.getSpecList().value
        //SpinnerAdapterSpec(a!!,requireContext())
        //spinnerSpec!!.adapter = SpinnerAdapterSpec(a!!, requireContext())
*/

/*
        model.setPatient().observe(activity!!, Observer { pat ->
            //model.updateSpecList()
        })
*/

/*
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) recyclerDoctor.layoutManager = GridLayoutManager(this.context, 2)
        else recyclerHistory.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        model.getHistoryList().observe(activity!!, Observer { hist ->
            //recyclerHistory!!.adapter = RecylcerAdapterHistory(model.getHistoryList().value!!, context!!, R.layout.card_history, model)
            //recyclerHistory.smoothScrollBy(80, 0)
        })
*/
/*
        model.getSpecList().observe(activity!!, Observer { specs ->
            //spinnerSpec!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specs)
            spinnerSpec!!.adapter = SpinnerAdapterSpec(specs, requireContext())
            //spinnerSpec!!.setSelection(model.pos_spec)
            //spinnerSpec!!.onItemSelectedListener = this
            //model.updateDocList()
            //model.cidSpec = specs[0]["IdSpesiality"]!!.toInt()
            //model.updateDocList()

        })

        recyclerDoctor.layoutManager = LinearLayoutManager(context)
        model.getDocList().observe(activity!!, Observer { docs ->
            //recyclerDoctor.adapter = RecylcerAdapterDoctor(model.getDocList())
            recyclerDoctor.adapter = RecylcerAdapterDoctor(docs)
        })
        */
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)
        binding.model1 = model
        return binding.root
        //return inflater.inflate(R.layout.fragment_1, container, false);
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var item = parent!!.adapter.getItem(position) as Map<String, String>
        when (parent.id) {
            R.id.spinnerLPU -> {
                model.pos_lpu = position
                model.cidLpu = item["IdLPU"]!!.toInt()
                model.setPatient()
            }
            /*
            R.id.spinnerSpec -> {
                if (model.pos_spec != position) {
                    model.pos_spec = position
                    model.cidSpec = item["IdSpesiality"]!!.toInt()

                    recyclerDoctor.adapter = RecylcerAdapterDoctor(model.getDocList().value!!)
                }
            }
            */
        }
        Storer(context!!).saveModel(model)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //model.setLpulist().removeObservers(activity!!)
        //model.setPatient().removeObservers(activity!!)
        //model.getHistoryList().removeObservers(activity!!)
        //model.getSpecList().removeObservers(activity!!)
        model.getDocList().removeObservers(activity!!)
    }

}
