package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R

class LicenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // SIMPLE LAYOUT - just buttons
        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(32, 32, 32, 32)
        
        val title = TextView(this)
        title.text = "💳 COFC GUARD License"
        title.textSize = 28f
        title.gravity = android.view.Gravity.CENTER
        title.setTextColor(android.graphics.Color.parseColor("#0055FF"))
        layout.addView(title)
        
        val trial = TextView(this)
        trial.text = "🎉 3-Day Free Trial Active!"
        trial.textSize = 18f
        trial.gravity = android.view.Gravity.CENTER
        trial.setTextColor(android.graphics.Color.parseColor("#00AA44"))
        layout.addView(trial)
        
        val monthly = Button(this)
        monthly.text = "📆 Monthly · €9.99"
        layout.addView(monthly)
        
        val yearly = Button(this)
        yearly.text = "📅 Yearly · €69.00"
        layout.addView(yearly)
        
        val lifetime = Button(this)
        lifetime.text = "♾️ Lifetime · €690.00"
        layout.addView(lifetime)
        
        val activate = Button(this)
        activate.text = "✅ Activate License"
        activate.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        layout.addView(activate)
        
        setContentView(layout)
    }
}
