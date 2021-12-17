package com.krachkovsky.mycalculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krachkovsky.mycalculator.R

class CalcActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fr_main, CalcFragment())
            .commit()
    }
}