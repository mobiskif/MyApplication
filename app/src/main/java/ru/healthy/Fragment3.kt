package ru.healthy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment

class Fragment3 : Fragment(), View.OnClickListener {

    lateinit var model: MyViewModel
    private lateinit var binding: ru.healthy.databinding.Fragment3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_3, container, false)
        binding.model3 = model
        binding.buttonYes.setOnClickListener(this)
        binding.buttonNo.setOnClickListener(this)
        return binding.root
        //return inflater.inflate(R.layout.fragment_1, container, false);
    }

    override fun onClick(v: View?) {
        model.from0=true
        if (v!!.id==R.id.buttonYes) {
            model.setAppointment(model.cidTalon)
            NavHostFragment.findNavController(this).navigate(R.id.Fragment1)
        }
        else NavHostFragment.findNavController(this).navigateUp()
    }
}
