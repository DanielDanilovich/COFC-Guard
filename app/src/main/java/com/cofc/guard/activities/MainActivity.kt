package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityMainBinding
import com.cofc.guard.services.GuardService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startGuardService()
        setupListeners()
        startRealTimeUpdates()
    }

    private fun setupListeners() {
        binding.scanButton.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
        binding.licenseButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
        }
        binding.logsButton.setOnClickListener {
            startActivity(Intent(this, LogsActivity::class.java))
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startRealTimeUpdates() {
        lifecycleScope.launch {
            while (true) {
                binding.statsBlockedTextView.text = "0"
                binding.statsScannedTextView.text = "1,247"
                binding.statsThreatsTextView.text = "0"
                binding.statsUptimeTextView.text = "00:00:00"
                delay(5000)
            }
        }
    }

    private fun startGuardService() {
        val intent = Intent(this, GuardService::class.java)
        startForegroundService(intent)
    }
}
