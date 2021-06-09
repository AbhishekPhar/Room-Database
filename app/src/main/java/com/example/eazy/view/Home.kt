package com.example.eazy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eazy.R
import com.example.eazy.util.ApplicationUtil

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApplicationUtil.homeactivity = this

        ApplicationUtil.openClass(MainFragment::class.java, supportFragmentManager , null )

    }
}