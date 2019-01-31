package ru.mobiskif

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.mobiskif.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        Storage(this).loadModel(model, Storage(this).restoreuser())


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.modelmain = model

        setTheme(R.style.AppTheme)
        //setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar2)
        //fab.setColorFilter(Color.WHITE)
        //fab.setOnClickListener { NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0) }

        model.cfam.observe(this, Observer { fam ->
            binding.invalidateAll()
        })

        if (model.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_menu0 -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
                true
            }
            R.id.nav_menu1 -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
                true
            }
            R.id.nav_menu2 -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
