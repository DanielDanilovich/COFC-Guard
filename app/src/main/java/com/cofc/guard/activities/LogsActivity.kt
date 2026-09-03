package com.cofc.guard.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLogsBinding

class LogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Simulate logs
        val logs = listOf(
            "🕒 21:45:12 ✅ HEARTBEAT OK",
            "🕒 21:44:58 ✅ HEARTBEAT OK",
            "🕒 21:44:44 ✅ HEARTBEAT OK",
            "🕒 21:44:30 ✅ QUANTUM LAYER 21 ACTIVE",
            "🕒 21:44:16 ✅ QUANTUM LAYER 20 ACTIVE",
            "🕒 21:44:02 ✅ SYSTEM INITIALIZED",
            "🕒 21:43:48 🚀 COFC GUARD STARTED"
        )
        binding.logsTextView.text = logs.joinToString("\n")

        binding.exportButton.setOnClickListener {
            Toast.makeText(this, "📤 Logs exported!", Toast.LENGTH_SHORT).show()
        }

        binding.clearButton.setOnClickListener {
            binding.logsTextView.text = "📁 Logs cleared"
            Toast.makeText(this, "🗑️ Logs cleared", Toast.LENGTH_SHORT).show()
        }
    }
}
