package com.cofc.guard.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanButton.setOnClickListener {
            if (isScanning) {
                stopScan()
            } else {
                startScan()
            }
        }
    }

    private fun startScan() {
        isScanning = true
        binding.scanButton.text = "⏹ Stop Scan"
        binding.scanButton.setBackgroundColor(getColor(R.color.status_error))
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.scanStatus.text = "🔍 Scanning..."

        // Simulate scan
        val handler = Handler(Looper.getMainLooper())
        var progress = 0
        handler.post(object : Runnable {
            override fun run() {
                if (isScanning) {
                    progress += 10
                    binding.progressBar.progress = progress
                    if (progress >= 100) {
                        binding.scanStatus.text = "✅ Scan Complete! No threats found."
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.scanButton.text = "🔄 Scan Again"
                        isScanning = false
                    } else {
                        handler.postDelayed(this, 300)
                    }
                }
            }
        })
    }

    private fun stopScan() {
        isScanning = false
        binding.scanButton.text = "▶ Start Scan"
        binding.scanButton.setBackgroundColor(getColor(R.color.primary))
        binding.scanStatus.text = "⏹ Scan Stopped"
        binding.progressBar.visibility = android.view.View.GONE
        binding.progressBar.progress = 0
    }
}
