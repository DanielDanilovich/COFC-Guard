package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.scanButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
        }
        binding.licenseButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
        }
        binding.logsButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
        }
    }
}
