package ru.mobiskif

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        Log.d("jop", "\n before load:\n" +
                "pos_user= ${model.pos_user}\n"+
                "pos_distr= ${model.pos_distr}\n"+
                "pos_lpu= ${model.pos_lpu}\n"+
                "pos_spec= ${model.pos_spec}\n"+
                "cidLpu= ${model.cidLpu}\n"+
                "cidSpec= ${model.cidSpec}\n"
        )
        Storage(this).loadModel(model, Storage(this).restoreuser())
        //activity!!.title = model.cfam.value + ' ' + model.cname.value
        //if (model.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar2)
        fab.setColorFilter(Color.WHITE);

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
