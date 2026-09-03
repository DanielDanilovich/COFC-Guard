package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.utils.LicenseUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val hasValidLicense = LicenseUtils.hasValidLicense(this)
            val intent = if (hasValidLicense) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LicenseActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 3000)
    }
}
