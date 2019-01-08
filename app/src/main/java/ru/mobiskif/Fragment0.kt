package ru.mobiskif

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
import kotlinx.android.synthetic.main.fragment_0.*
import kotlinx.android.synthetic.main.activity_main.*

class Fragment0 : Fragment() {
    private val J = "jop"
    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.cuser.observe(this, Observer<Any> { updateUI() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.mobiskif.databinding.Fragment0Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model0 = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val radioButton = radioGroup.getChildAt(mModel.cuser.value!!) as RadioButton
        radioButton.isChecked = true
        radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            val position = radioGroup.indexOfChild(radioGroup.findViewById<View>(i))
            mModel.loadUser(position)
        }

        spinnerDistrict.adapter = SpinnerAdapter(mModel.getDistrictList().value!!, context)

        saveButton.setOnClickListener {
            mModel.cname.value = editName.text.toString()
            mModel.cfam.value = editSecondname.text.toString()
            mModel.cotch.value = editSurname.text.toString()
            mModel.cdate.value = editBirstdate.text.toString()
            mModel.cdistrict = spinnerDistrict.selectedItemPosition
            if (mModel.cdate.value!!.length > 9) {
                mModel.saveUser()
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
            }
            else Snackbar.make(this.view!!, "Дата рождения должна быть вида '1984-07-23'", Snackbar.LENGTH_LONG).show()

        }
        //activity!!.appbar.setExpanded(true,true)
        updateUI()
    }

    private fun updateUI() {
        editName.text.clear(); editName.text.insert(0, mModel.cname.value)
        editSecondname.text.clear(); editSecondname.text.insert(0, mModel.cfam.value)
        editSurname.text.clear(); editSurname.text.insert(0, mModel.cotch.value)
        editBirstdate.text.clear(); editBirstdate.text.insert(0, mModel.cdate.value)
        spinnerDistrict.setSelection(mModel.cdistrict)
        //activity!!.collapsing_toolbar.title = mModel.cname.value + ' ' + mModel.cfam.value
        activity!!.title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value

    }

}
