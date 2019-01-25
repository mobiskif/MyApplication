package ru.mobiskif

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_0.*

class Fragment0 : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment0Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        model.getDistrlist().observe(activity!!, Observer<List<String>> { distr ->
            spinnerDistrict.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, distr)
            spinnerDistrict.setSelection(model.pos_distr)
            spinnerDistrict.onItemSelectedListener = this
        })

        model.getUser().observe(activity!!, Observer<Int> {
            val radioButton = radioGroup.getChildAt(model.pos_user) as RadioButton
            radioButton.isChecked = true
            radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
                val position = radioGroup.indexOfChild(radioGroup.findViewById<View>(i))
                Storage(activity!!).loadModel(model, position)
                binding.invalidateAll()
                spinnerDistrict.setSelection(model.pos_distr)
                activity!!.title = model.cfam.value + ' ' + model.cname.value + ' ' + model.cdate.value
            }
        })

        saveButton.setOnClickListener {
            model.cdate.value = editBirstdate.text.toString()
            if (model.cdate.value!!.length > 9) {
                model.cname.value=editName.text.toString()
                model.cfam.value=editSecondname.text.toString()
                model.cotch.value=editSurname.text.toString()
                model.cdate.value=editBirstdate.text.toString()
                //model.pos_user=radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.checkedRadioButtonId))
                Storage(context!!).saveModel(model)
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
            }
            else Snackbar.make(this.view!!, "Дата рождения должна быть вида '1984-07-23'", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model0 = model
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (model.pos_distr != position) {
            model.pos_distr = position
            model.updateLpuList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.getDistrlist().removeObservers(activity!!)
        model.getUser().removeObservers(activity!!)
    }
}
