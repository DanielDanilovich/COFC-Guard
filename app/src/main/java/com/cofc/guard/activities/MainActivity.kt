package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Minimal test
        val tv = TextView(this)
        tv.text = "✅ COFC GUARD\nVersion 3.3.0\n\nApp is Running!\nNo crashes."
        tv.textSize = 28f
        tv.gravity = android.view.Gravity.CENTER
        tv.setPadding(32, 32, 32, 32)
        setContentView(tv)
    }
}
