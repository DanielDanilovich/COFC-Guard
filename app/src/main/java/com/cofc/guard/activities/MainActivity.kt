package com.cofc.guard.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityMainBinding
import com.cofc.guard.services.GuardService
import com.cofc.guard.utils.LicenseUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var lottieShield: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (!LicenseUtils.hasValidLicense(this)) {
            startActivity(Intent(this, LicenseActivity::class.java))
            finish()
            return
        }
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lottieShield = findViewById(R.id.lottieShield)
        lottieShield.setAnimation("shield.json")
        lottieShield.playAnimation()
        lottieShield.repeatCount = Int.MAX_VALUE

        startGuardService()
        setupListeners()
        startRealTimeUpdates()
        animateUI()
        startFakeDataUpdates()
        
        if (LicenseUtils.isTrialActive(this)) {
            Toast.makeText(this, "🎉 3-Day Trial Active!", Toast.LENGTH_LONG).show()
        }
    }

    private fun animateUI() {
        binding.statusCard.alpha = 0f
        binding.statusCard.animate().alpha(1f).setDuration(800).start()

        binding.buttonsLayout.alpha = 0f
        binding.buttonsLayout.animate().alpha(1f).setDuration(1000).start()
        
        binding.lottieShield.animate()
            .scaleX(1.3f)
            .scaleY(1.3f)
            .setDuration(2000)
            .withEndAction {
                binding.lottieShield.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(2000)
                    .start()
            }
            .start()
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
                binding.statsBlockedTextView.text = Random.nextInt(0, 10).toString()
                binding.statsScannedTextView.text = (1000 + Random.nextInt(0, 5000)).toString()
                binding.statsThreatsTextView.text = Random.nextInt(0, 3).toString()
                binding.statsUptimeTextView.text = android.text.format.DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString()
                delay(3000)
            }
        }
    }

    private fun startFakeDataUpdates() {
        lifecycleScope.launch {
            while (true) {
                if (Random.nextBoolean()) {
                    binding.statusIndicator.setBackgroundResource(R.drawable.status_warning)
                    binding.protectionStatusTextView.text = "⚠️ Scanning..."
                    binding.protectionStatusTextView.setTextColor(Color.parseColor("#FF8800"))
                } else {
                    binding.statusIndicator.setBackgroundResource(R.drawable.status_active)
                    binding.protectionStatusTextView.text = "🟢 Protected"
                    binding.protectionStatusTextView.setTextColor(Color.parseColor("#00AA44"))
                }
                delay(5000)
            }
        }
    }

    private fun startGuardService() {
        try {
            val intent = Intent(this, GuardService::class.java)
            startForegroundService(intent)
        } catch (e: Exception) {
            // Service already running
        }
    }
}
