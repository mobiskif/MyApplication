package ru.mobiskif.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.main_activiry.*

class ActivityMain : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout
    var iscreated = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activiry)
        //setSupportActionBar(detail_toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, detail_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show() }

    }

    override fun onSupportNavigateUp() = findNavController(nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_slideshow -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.blankFragment0)
                return true
            }
            R.id.nav_manage -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.blankFragment1)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
