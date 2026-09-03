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

        // Update status
        updateLicenseStatus()

        // Activate button
        binding.activateButton.setOnClickListener {
            val key = binding.licenseInputEditText.text.toString().trim()
            if (key.isNotEmpty()) {
                if (CryptoUtils.validateLicense(key)) {
                    LicenseUtils.saveLicense(this, key, "lifetime")
                    Toast.makeText(this, "✅ License Activated!", Toast.LENGTH_LONG).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "❌ Invalid License Key", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter a license key", Toast.LENGTH_SHORT).show()
            }
        }

        // Purchase buttons
        binding.monthlyButton.setOnClickListener {
            showPurchaseDialog("Monthly", "€9.99")
        }
        binding.yearlyButton.setOnClickListener {
            showPurchaseDialog("Yearly", "€69.00")
        }
        binding.lifetimeButton.setOnClickListener {
            showPurchaseDialog("Lifetime", "€690.00")
        }
    }

    private fun updateLicenseStatus() {
        val status = LicenseUtils.getLicenseStatus(this)
        binding.licenseStatusTextView.text = status
        
        when (status) {
            "Active", "Lifetime" -> {
                binding.licenseStatusTextView.setTextColor(getColor(R.color.status_active))
                navigateToMain()
            }
            "Expired" -> {
                binding.licenseStatusTextView.setTextColor(getColor(R.color.status_error))
            }
            else -> {
                binding.licenseStatusTextView.setTextColor(getColor(R.color.status_inactive))
            }
        }
    }

    private fun showPurchaseDialog(plan: String, price: String) {
        Toast.makeText(this, "$plan Plan: $price - USDT TRC20", Toast.LENGTH_LONG).show()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
