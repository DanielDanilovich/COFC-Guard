package com.cofc.guard.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLicenseBinding

class LicenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Demo mode - always show as active
        binding.licenseStatusTextView.text = "🟢 Active (Demo)"
        binding.licenseStatusTextView.setTextColor(ContextCompat.getColor(this, R.color.status_active))
        binding.licenseKeyTextView.text = "COFC-DEMO-2A1B-3C4D"

        // Activate
        binding.activateButton.setOnClickListener {
            val key = binding.licenseInputEditText.text.toString().trim()
            if (key.isNotEmpty()) {
                Toast.makeText(this, "✅ Sovereign Key Activated! (Demo)", Toast.LENGTH_LONG).show()
                navigateToMain()
            } else {
                Toast.makeText(this, "Please enter your Sovereign Key", Toast.LENGTH_SHORT).show()
            }
        }

        // Plans - Open Payment Gateway
        binding.monthlyButton.setOnClickListener {
            openPaymentGateway("Monthly", "9.99")
        }
        binding.yearlyButton.setOnClickListener {
            openPaymentGateway("Yearly", "69.00")
        }
        binding.lifetimeButton.setOnClickListener {
            openPaymentGateway("Lifetime", "690.00")
        }
    }

    private fun openPaymentGateway(plan: String, amount: String) {
        // Open PayRam payment page
        val paymentUrl = "https://guard.cofc.io/pay?plan=$plan&amount=$amount"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl))
        startActivity(intent)
        
        Toast.makeText(this, "💳 Redirecting to payment...", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
