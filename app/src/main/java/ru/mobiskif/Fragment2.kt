package ru.mobiskif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_2.*

class Fragment2 : Fragment() {

    lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.Fragment2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        model.setTalonList()
    }

    override fun onResume() {
        super.onResume()
        recyclerTalon.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        model.talonlist.observe(activity!!, Observer { items ->
            recyclerTalon.adapter = RecylcerAdapterTalons(items, this)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_2, container, false)
        binding.model2 = model
        return binding.root
        //return inflater.inflate(R.layout.fragment_1, container, false);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.talonlist.removeObservers(activity!!)
    }

}
