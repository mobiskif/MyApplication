package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.myfragment.*


class BlankFragment0 : Fragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        mModel.changeUser(checkedId)

    }

    override fun onResume() {
        super.onResume()
        rGroup.setOnCheckedChangeListener(this)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.context = context

        val mObserver1 = Observer<List<String>> {
            spinner0.adapter = MySpinnerAdapter(mModel.getDistrictList().value!!, this.context)
            spinner0.onItemSelectedListener = spinner0_OnItemSelectedListener(mModel)
        }
        mModel.getDistrictList().observe(this, mObserver1)

        val mObserver0 = Observer<Any>{
            textView6.text= mModel.getName().value
        }
        mModel.getName().observe(this, mObserver0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.myfragment, container, false)
        //val binding : Fragment0Binding = DataBindingUtil.inflate(inflater, R.layout.myfragment, container, false)
        //binding.setVariable(BR.mModel, mModel)
        //binding.executePendingBindings()
        return inflater.inflate(R.layout.myfragment, container, false)
        //return binding.root
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
