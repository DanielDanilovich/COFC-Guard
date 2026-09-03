package com.cofc.guard.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Minimal layout - just a text
        val tv = TextView(this)
        tv.text = "COFC GUARD\nVersion 2.7.0\nApp is Running!"
        tv.textSize = 24f
        tv.gravity = android.view.Gravity.CENTER
        setContentView(tv)
    }
}
