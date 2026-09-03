package com.cofc.guard.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLicenseBinding
import com.cofc.guard.utils.CryptoUtils

class LicenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activateButton.setOnClickListener {
            val key = binding.licenseInputEditText.text.toString().trim()
            if (key.isNotEmpty()) {
                if (CryptoUtils.validateLicense(key)) {
                    Toast.makeText(this, "✅ License Activated!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "❌ Invalid License Key", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter a license key", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
