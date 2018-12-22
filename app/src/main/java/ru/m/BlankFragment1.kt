package ru.m

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*

class BlankFragment1 : Fragment() {
    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")

        val mObserver1 = Observer<List<String>> {
            recycler1.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            recycler1.adapter = MyRecylcerAdapter(mModel.getLpuList().value!!, this.context)
            recycler1.post(Runnable { recycler1.smoothScrollBy(105, 0) })
            spinner.adapter = MySpinnerAdapter(mModel.getLpuList().value!!, this.context)
            spinner.setOnItemSelectedListener(spinner1_OnItemSelectedListener(mModel))
        }
        mModel.getLpuList().observe(this, mObserver1)

        val mObserver2 = Observer<List<String>> {
            recycler2.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            recycler2.adapter = MyRecylcerAdapter(mModel.getSpecialityList().value!!, this.activity)
            recycler2.post({ recycler2.smoothScrollBy(75, 0) })
        }
        mModel.getSpecialityList().observe(this, mObserver2)

        val mObserver3 = Observer<List<String>> {
            recycler3.layoutManager = LinearLayoutManager(this.context)
            recycler3.adapter = MyRecylcerAdapter(mModel.getDoctorList().value!!, this.context)
            //recycler3.post(Runnable { recycler3.smoothScrollBy(105, 0) })
        }
        mModel.getDoctorList().observe(this, mObserver3)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank_fragment1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button1.setOnClickListener { mModel.updateLpuList() }
    }

}

class spinner1_OnItemSelectedListener(private val mModel: MyDataModel) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("onNothingSelected not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //mModel.updateLpuList()
        Log.d("jop","=== $position")
    }

}