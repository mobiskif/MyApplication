package ru.mobiskif

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mModel = run { ViewModelProviders.of(this).get(MainViewModel::class.java) }
        mModel.init(this)
        title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value

        //val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.modelmain = mModel

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show()
            NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.action_Fragment2_to_help)
        }
        fab.visibility = View.INVISIBLE

        if (mModel.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
        else NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)

    }

    override fun onSupportNavigateUp() = findNavController(nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_menu0 -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
                return true
            }
            R.id.nav_menu1 -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)
                return true
            }
            R.id.nav_menu2 -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
