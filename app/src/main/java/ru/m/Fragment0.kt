package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_0.*


class Fragment0 : Fragment(), RadioGroup.OnCheckedChangeListener {

    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.context = context

        val observerDistrict = Observer<List<String>> {
            spinnerDistrict.adapter = MySpinnerAdapter(mModel.getDistrictList().value!!, this.context)
            spinnerDistrict.onItemSelectedListener = spinner0_OnItemSelectedListener(mModel)
        }
        mModel.getDistrictList().observe(this, observerDistrict)

        val observerName = Observer<Any>{
            editName.text.clear()
            editName.text.insert(0,it.toString())
        }
        mModel.getName().observe(this, observerName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.m.databinding.Fragment0Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_0, container, false)
        binding.model = mModel
        //return inflater.inflate(R.layout.fragment_0, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var radioButton = radioGroup.getChildAt(mModel.getCurrentUser().value!!-1) as RadioButton
        radioButton.isChecked=true
        onCheckedChanged(radioGroup, radioButton.id)
        radioGroup.setOnCheckedChangeListener(this)
        saveButton.setOnClickListener {mModel.store()}
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        var idUser= group?.indexOfChild(group?.findViewById<View>(checkedId))
        mModel.changeUser(idUser!!)
    }

}

class spinner0_OnItemSelectedListener(private val mModel: MyDataModel) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("onNothingSelected not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mModel.currentDistrict = "$position"
        mModel.updateLpuList()
    }

}
