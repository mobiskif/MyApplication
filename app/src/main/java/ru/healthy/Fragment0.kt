package ru.healthy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class Fragment0 : Fragment() {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.healthy.databinding.Fragment0Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        model.setDistrlist()
    }

    override fun onResume() {
        super.onResume()

        model.distrlist.observe(activity!!, Observer { items ->
            spinnerDistrict.adapter = SpinnerAdapter(items, requireContext())
            spinnerDistrict.setSelection(model.pos_distr)
        })

        model.getUserID().observe(activity!!, Observer<Int> {
            val radioButton = radioGroup.getChildAt(model.pos_user) as RadioButton
            radioButton.isChecked = true
            radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
                val position = radioGroup.indexOfChild(radioGroup.findViewById<View>(i))
                Storer(activity!!).loadModel(model, position)
                binding.invalidateAll()
                spinnerDistrict.setSelection(model.pos_distr)
            }
        })

        saveButton.setOnClickListener {
            model.cdate.value = editBirstdate.text.toString()
            if (model.cdate.value!!.length > 9) {
                model.cname.value=editName.text.toString()
                model.cfam.value=editSecondname.text.toString()
                model.cotch.value=editSurname.text.toString()
                model.cdate.value=editBirstdate.text.toString()
                model.pos_distr = spinnerDistrict.selectedItemPosition
                model.from0=true
                model.cidPatError.value=""
                //model.cidLpu = (spinnerDistrict.selectedItem as Map<String, String>)["IdDistrict"]!!.toInt()
                Storer(requireContext()).saveModel(model)
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
            }
            else model.cerror.postValue("Дата рождения должна быть вида '1984-07-23'")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model0 = model
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.distrlist.removeObservers(activity!!)
        model.getUserID().removeObservers(activity!!)
    }
}
