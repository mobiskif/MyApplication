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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_blank_fragment0.*

class BlankFragment0 : Fragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        Snackbar.make(this.view!!, "Replace with your own action", Snackbar.LENGTH_LONG).show()
        //Toast.makeText(this.context,"qwerwer", Toast.LENGTH_SHORT).show()
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

        val mObserver1 = Observer<List<String>> {
            spinner0.adapter = MySpinnerAdapter(mModel.getDistrictList().value!!, this.context)
            spinner0.onItemSelectedListener = spinner0_OnItemSelectedListener(mModel)
        }
        mModel.getDistrictList().observe(this, mObserver1)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank_fragment0, container, false)
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
