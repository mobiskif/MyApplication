package ru.healthy

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.healthy.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        //setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        Storer(this).loadModel(model, Storer(this).restoreuser())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.modelmain = model

        setSupportActionBar(toolbar)
        //fab.setColorFilter(Color.WHITE)
        //fab.setOnClickListener { NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0) }
        model.cfam.observe(this, Observer { binding.invalidateAll() })
        model.cerror.observe(this, Observer { Snackbar.make(constraintLayout, it, Snackbar.LENGTH_LONG).show() })
        if (model.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
    }

    //override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()

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
