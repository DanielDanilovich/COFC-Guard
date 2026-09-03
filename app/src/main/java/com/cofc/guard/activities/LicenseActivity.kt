package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLicenseBinding
import com.cofc.guard.utils.CryptoUtils
import com.cofc.guard.utils.LicenseUtils

class LicenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show trial status
        val isTrial = LicenseUtils.isTrialActive(this)
        binding.trialStatus.text = if (isTrial) "🎉 3-Day Free Trial Active" else "⏰ Trial Expired"
        
        // Purchase buttons
        binding.monthlyButton.setOnClickListener {
            Toast.makeText(this, "📆 Monthly: €9.99 - USDT TRC20", Toast.LENGTH_LONG).show()
        }
        binding.yearlyButton.setOnClickListener {
            Toast.makeText(this, "📅 Yearly: €69.00 - USDT TRC20", Toast.LENGTH_LONG).show()
        }
        binding.lifetimeButton.setOnClickListener {
            Toast.makeText(this, "♾️ Lifetime: €690.00 - USDT TRC20", Toast.LENGTH_LONG).show()
        }

        // Activate license
        binding.activateButton.setOnClickListener {
            val key = binding.licenseInputEditText.text.toString().trim()
            if (key.isNotEmpty()) {
                if (CryptoUtils.validateLicense(key)) {
                    LicenseUtils.activateLicense(this, key, "lifetime")
                    Toast.makeText(this, "✅ License Activated!", Toast.LENGTH_LONG).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "❌ Invalid License Key", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter your license key", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
