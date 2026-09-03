package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ULTRA SIMPLE - just show text, no crashes
        val tv = TextView(this)
        tv.text = """
            🛡️ COFC GUARD
            Version 4.1.2
            
            ✅ App is Running!
            
            No crashes.
            No services.
            Just works.
            
            🌍🔑😋👑✍️💚🫆
        """.trimIndent()
        tv.textSize = 22f
        tv.gravity = android.view.Gravity.CENTER
        tv.setPadding(32, 32, 32, 32)
        tv.setTextColor(android.graphics.Color.parseColor("#0055FF"))
        setContentView(tv)
    }
}
