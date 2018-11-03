package ru.mobiskif.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
