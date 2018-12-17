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
import kotlinx.android.synthetic.main.fragment_blank_fragment0.*
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*

class BlankFragment0 : Fragment(), AdapterView.OnItemSelectedListener {
    private var mModel: RecyclerViewModel? = null

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //mModel!!.update2()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mModel = activity?.run { ViewModelProviders.of(this).get(RecyclerViewModel::class.java) } ?: throw Exception("Invalid Activity")
        val mObserver1 = Observer<List<String>> {
            spinner0.adapter = SpinnerBaseAdapter(mModel!!.getList1().value!!, this.context)
            spinner0.setOnItemSelectedListener(this)
        }
        mModel!!.getList1().observe(this, mObserver1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank_fragment0, container, false)
    }
}
