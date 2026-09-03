package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLicenseBinding
import com.cofc.guard.payment.PaymentSystem
import com.cofc.guard.utils.CryptoUtils
import com.cofc.guard.utils.LicenseUtils

class LicenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        animateUI()
    }

    private fun setupUI() {
        val isTrial = LicenseUtils.isTrialActive(this)
        binding.trialStatus.text = if (isTrial) "🎉 3-Day Free Trial Active" else "⏰ Trial Expired"
        binding.trialStatus.setTextColor(if (isTrial) ContextCompat.getColor(this, R.color.status_active) else ContextCompat.getColor(this, R.color.status_error))
        binding.licenseStatus.text = "Status: ${if (LicenseUtils.hasValidLicense(this)) "✅ Active" else "❌ Inactive"}"
        
        // Show customer info
        val customer = PaymentSystem.getCustomer(this)
        if (customer != null) {
            binding.customerName.text = "👤 ${customer.name}"
            binding.customerEmail.text = "📧 ${customer.email}"
            binding.totalSpent.text = "💰 Total Spent: $${PaymentSystem.getTotalSpent(this)}"
        }
    }

    private fun animateUI() {
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeIn.duration = 800
        binding.licenseCard.startAnimation(fadeIn)
    }

    private fun setupListeners() {
        // Crypto Payments (USDT TRC20)
        binding.monthlyCrypto.setOnClickListener {
            processPayment("Monthly", 9.99, "USDT TRC20")
        }
        binding.yearlyCrypto.setOnClickListener {
            processPayment("Yearly", 69.00, "USDT TRC20")
        }
        binding.lifetimeCrypto.setOnClickListener {
            processPayment("Lifetime", 690.00, "USDT TRC20")
        }
        
        // Credit Card Payments
        binding.monthlyCard.setOnClickListener {
            processPayment("Monthly", 9.99, "Credit Card")
        }
        binding.yearlyCard.setOnClickListener {
            processPayment("Yearly", 69.00, "Credit Card")
        }
        binding.lifetimeCard.setOnClickListener {
            processPayment("Lifetime", 690.00, "Credit Card")
        }
        
        binding.activateButton.setOnClickListener {
            val key = binding.licenseInput.text.toString().trim()
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

    private fun processPayment(plan: String, amount: Double, method: String) {
        val customer = PaymentSystem.getCustomer(this)
        if (customer == null) {
            // Create customer if not exists
            val deviceId = CryptoUtils.generateHash(android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.ANDROID_ID))
            PaymentSystem.createCustomer(this, "Guest", "guest@example.com", deviceId)
        }
        
        if (PaymentSystem.processPayment(this, amount, method)) {
            // Activate license
            val licenseKey = CryptoUtils.generateHash("${plan}:${System.currentTimeMillis()}:${customer?.id}")
            LicenseUtils.activateLicense(this, licenseKey, plan.lowercase())
            
            Toast.makeText(this, "✅ Payment Successful!\n$plan Plan - $method\nLicense Activated!", Toast.LENGTH_LONG).show()
            navigateToMain()
        } else {
            Toast.makeText(this, "❌ Payment Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
