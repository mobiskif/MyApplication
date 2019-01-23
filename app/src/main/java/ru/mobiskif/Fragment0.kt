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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        activity!!.title = model.cfam.value + ' ' + model.cname.value + ' ' + model.cdate.value
        model.getDistrlist().observe(activity!!, Observer<List<String>> { distr ->
            spinnerDistrict.adapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, distr)
            spinnerDistrict.setSelection(model.pos_distr)
            spinnerDistrict.onItemSelectedListener = this
            Log.d("jop", "Сработал District Observer")
        })

        model.getUser().observe(activity!!, Observer<Int> {
            val radioButton = radioGroup.getChildAt(model.pos_user) as RadioButton
            radioButton.isChecked = true
            radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
                val position = radioGroup.indexOfChild(radioGroup.findViewById<View>(i))
                model.pos_user=position
                model.getUser()
                Log.d("jop", "onChekListener($position)")
            }
            Log.d("jop", "Сработал User Observer")
        })

        saveButton.setOnClickListener {
            /*
            mModel.cname.value = editName.text.toString()
            mModel.cfam.value = editSecondname.text.toString()
            mModel.cotch.value = editSurname.text.toString()
            mModel.cdate.value = editBirstdate.text.toString()
            model.cdistrict = spinnerDistrict.selectedItemPosition + 1
            if (mModel.cdate.value!!.length > 9) {
                mModel.saveUser(this.context!!)
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
            }
            else Snackbar.make(this.view!!, "Дата рождения должна быть вида '1984-07-23'", Snackbar.LENGTH_LONG).show()
            */

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment0Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model0 = model
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    private fun updateUI() {
        //editName.text.clear(); editName.text.insert(0, model.cname.value)
        //editSecondname.text.clear(); editSecondname.text.insert(0, model.cfam.value)
        //editSurname.text.clear(); editSurname.text.insert(0, model.cotch.value)
        //editBirstdate.text.clear(); editBirstdate.text.insert(0, model.cdate.value)
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        model.pos_distr = position
        model.getLpulist()
        Log.d("jop", "onItemSelected($position)")
    }

}
