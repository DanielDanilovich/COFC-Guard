package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityMainBinding
import com.cofc.guard.services.GuardService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var lottieShield: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lottie Animation
        lottieShield = findViewById(R.id.lottieShield)
        lottieShield.setAnimation("shield.json")
        lottieShield.playAnimation()
        lottieShield.repeatCount = Int.MAX_VALUE // במקום INFINITE

        startGuardService()
        setupListeners()
        startRealTimeUpdates()
        animateUI()
    }

    private fun animateUI() {
        binding.statusCard.alpha = 0f
        binding.statusCard.animate().alpha(1f).setDuration(800).start()

        binding.buttonsLayout.alpha = 0f
        binding.buttonsLayout.animate().alpha(1f).setDuration(1000).start()
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
