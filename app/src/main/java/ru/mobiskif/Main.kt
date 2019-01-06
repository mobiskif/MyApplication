package ru.mobiskif

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.main_activity.*
import ru.mobiskif.databinding.MainActivityBinding

class Main : AppCompatActivity() {
    private lateinit var mModel: MyDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mModel = run { ViewModelProviders.of(this).get(MyDataModel::class.java) }
        mModel.init(this)

        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        title = mModel.cfam.value + ' ' + mModel.cname.value + ' ' + mModel.cdate.value

        //val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        //binding.modelmain = mModel

        //setSupportActionBar(detail_toolbar)
/*
        val toggle = ActionBarDrawerToggle(this, drawer_layout, detail_toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(toggle)
        //toggle.syncState()

        nav_view.setNavigationItemSelectedListener { menuItem ->
            //menuItem.isChecked = true
            onOptionsItemSelected(menuItem)
            drawer_layout.closeDrawers()
            true
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show()
        }
*/
        if (mModel.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
        else NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment1)

    }


//override fun onSupportNavigateUp() = findNavController(nav_host_fragment).navigateUp()



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
