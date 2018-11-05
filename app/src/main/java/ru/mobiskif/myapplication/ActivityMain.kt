package ru.mobiskif.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity1.*
import kotlinx.android.synthetic.main.activity1draw.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content.*

class ActivityMain : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1draw)
        //setSupportActionBar(detail_toolbar)

        //mDrawerLayout = drawer_layout

        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show() }

        val toggle = ActionBarDrawerToggle(this, drawer_layout, detail_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawer_layout.closeDrawers()
            true
        }



        radiogoup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            Snackbar.make(radioGroup, "" + i, Snackbar.LENGTH_SHORT).show()
            NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.action_blankFragment0_to_blankFragment1)
        }

    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_slideshow -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.action_blankFragment0_to_blankFragment1)
                return true
            }
            R.id.nav_manage -> {
                NavHostFragment.findNavController(nav_host_fragment).navigateUp()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}

/*
        if (savedInstanceState == null) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, BlankFragment0.newInstance("qwe", "asd"))
                .commitNow()
        }
*/
