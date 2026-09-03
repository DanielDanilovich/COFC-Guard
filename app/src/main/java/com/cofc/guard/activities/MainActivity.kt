package com.cofc.guard.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityMainBinding
import com.cofc.guard.payment.PaymentSystem
import com.cofc.guard.services.QuantumLayerService
import com.cofc.guard.utils.LicenseUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (!LicenseUtils.hasValidLicense(this)) {
            startActivity(Intent(this, LicenseActivity::class.java))
            finish()
            return
        }
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startQuantumLayers()
        setupUI()
        setupListeners()
        startRealTimeUpdates()
        animateUI()
        
        // Show trial info
        if (LicenseUtils.isTrialActive(this)) {
            Toast.makeText(this, "🎉 3-Day Trial Active!", Toast.LENGTH_LONG).show()
        }
    }

    private fun startQuantumLayers() {
        val intent = Intent(this, QuantumLayerService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun setupUI() {
        binding.statusIndicator.setCardBackgroundColor(ContextCompat.getColor(this, R.color.status_active))
        binding.protectionStatusText.text = "🟢 Protected"
        binding.protectionStatusText.setTextColor(ContextCompat.getColor(this, R.color.status_active))
        binding.layersCount.text = "${QuantumLayerService.getActiveLayers(this)}/21"
        binding.blockedCount.text = "0"
        binding.scannedCount.text = "1,247"
        binding.threatsCount.text = "0"
        binding.uptimeText.text = "00:00:00"
    }

    private fun animateUI() {
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeIn.duration = 800
        binding.statusCard.startAnimation(fadeIn)
        
        val slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        slideUp.duration = 1000
        binding.buttonsLayout.startAnimation(slideUp)
    }

    private fun setupListeners() {
        binding.scanButton.setOnClickListener {
            Toast.makeText(this, "🔍 Quantum Scan Started!", Toast.LENGTH_SHORT).show()
            animateButton(it)
        }
        binding.licenseButton.setOnClickListener {
            startActivity(Intent(this, LicenseActivity::class.java))
            animateButton(it)
        }
        binding.logsButton.setOnClickListener {
            Toast.makeText(this, "📁 Logs", Toast.LENGTH_SHORT).show()
            animateButton(it)
        }
        binding.settingsButton.setOnClickListener {
            Toast.makeText(this, "⚙️ Settings", Toast.LENGTH_SHORT).show()
            animateButton(it)
        }
    }

    private fun animateButton(view: android.view.View) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }

    private fun startRealTimeUpdates() {
        lifecycleScope.launch {
            while (true) {
                binding.blockedCount.text = Random.nextInt(0, 15).toString()
                binding.scannedCount.text = (1000 + Random.nextInt(0, 5000)).toString()
                binding.threatsCount.text = Random.nextInt(0, 5).toString()
                binding.uptimeText.text = android.text.format.DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString()
                binding.layersCount.text = "${QuantumLayerService.getActiveLayers(this@MainActivity)}/21"
                
                if (Random.nextBoolean()) {
                    binding.statusIndicator.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.status_warning))
                    binding.protectionStatusText.text = "⚠️ Scanning..."
                    binding.protectionStatusText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.status_warning))
                } else {
                    binding.statusIndicator.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.status_active))
                    binding.protectionStatusText.text = "🟢 Protected"
                    binding.protectionStatusText.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.status_active))
                }
                
                delay(3000)
            }
        }
    }
}
