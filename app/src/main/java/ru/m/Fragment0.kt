package ru.m

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
import kotlinx.android.synthetic.main.fragment_0.*
import kotlinx.android.synthetic.main.main_activity.*

class Fragment0 : Fragment() {
    val J = "jop"
    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.init(context)

        //val observerUser = Observer<Any> { updateUI() }
        mModel.cid.observe(this, Observer<Any> { updateUI() })
    }

    private fun updateUI() {
        editName.text.clear(); editName.text.insert(0, mModel.cname.value)
        spinnerDistrict.setSelection(mModel.cdistrict)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.m.databinding.Fragment0Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val radioButton = radioGroup.getChildAt(mModel.cid.value!!) as RadioButton
        radioButton.isChecked = true
        radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            val position = radioGroup.indexOfChild(radioGroup.findViewById<View>(i))
            mModel.changeUser(position)
        }

        spinnerDistrict.adapter = MySpinnerAdapter(mModel.getDistrictList().value!!, context)

        saveButton.setOnClickListener {
            mModel.cname.value = editName.text.toString()
            mModel.cdistrict = spinnerDistrict.selectedItemPosition
            mModel.saveUser()
            NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
        }
        updateUI()
    }

}
