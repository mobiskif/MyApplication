package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*


class BlankFragment1 : Fragment() {
    private lateinit var mModel: RecyclerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mModel = ViewModelProviders.of(this).get(RecyclerViewModel::class.java)

        val mObserver1 = Observer<List<String>> {
            recycler1.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            recycler1.adapter = RecylcerAdapter(mModel.getList1().value!!, this.context)
            recycler1.post(Runnable { recycler1.smoothScrollBy(105, 0) })
        }
        mModel.getList1().observe(this, mObserver1)

        val mObserver2 = Observer<List<String>> {
            recycler2.layoutManager = LinearLayoutManager(this.context)
            recycler2.adapter = RecylcerAdapter(mModel.getList2().value!!, this.context)
        }
        mModel.getList2().observe(this, mObserver2)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button1.setOnClickListener {
            mModel.update()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(ru.m.R.layout.fragment_blank_fragment1, container, false)
    }

}
