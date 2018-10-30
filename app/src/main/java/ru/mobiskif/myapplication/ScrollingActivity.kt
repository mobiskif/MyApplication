package ru.mobiskif.myapplication

import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.app_bar.*

//import kotlinx.android.synthetic.main.app_bar_main2.*


class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = getWindow()
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        if (Build.VERSION.SDK_INT >= 21) {
            //window.setStatusBarColor(Color.TRANSPARENT)
            //window.setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryColor))
        }

        setContentView(R.layout.activity_scrolling)
        //setSupportActionBar(detail_toolbar)
        setSupportActionBar(detail_toolbar)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
