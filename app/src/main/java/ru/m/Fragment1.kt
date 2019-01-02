package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_1.*

class Fragment1 : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mModel = activity?.run { ViewModelProviders.of(this).get(MyDataModel::class.java) } ?: throw Exception("Invalid Activity")
        mModel.clpu.observe(this, Observer<Any> {
            Snackbar.make(this.view!!, "Replace with your own action", Snackbar.LENGTH_LONG).show()
            //recycler4.adapter!!.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        spinner1.adapter = MySpinnerAdapter(mModel.getLpuList().value!!, context)
        spinner1.setOnItemSelectedListener(this)

        spinner2.adapter = MySpinnerAdapter(mModel.getSpecialityList().value!!, context)

        recycler3.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recycler3.adapter = MyRecylcerAdapter(mModel.getDoctorList().value!!, context)
        recycler3.smoothScrollBy(100, 0)

        recycler4.layoutManager = LinearLayoutManager(this.context)
        recycler4.adapter = MyListAdapter(mModel.getDoctorList().value!!, context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mModel.clpu.value=position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
