package com.cofc.guard.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cofc.guard.R
import com.cofc.guard.database.LogsDatabase
import com.cofc.guard.models.ActivityLog
import com.cofc.guard.databinding.ActivityLogsBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogsBinding
    private lateinit var db: LogsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LogsDatabase.getInstance(this)

        loadLogs()

        binding.exportButton.setOnClickListener {
            Toast.makeText(this, "📤 Logs exported!", Toast.LENGTH_SHORT).show()
        }

        binding.clearButton.setOnClickListener {
            lifecycleScope.launch {
                db.logDao().clearLogs()
                binding.logsTextView.text = "📁 Logs cleared"
                Toast.makeText(this@LogsActivity, "🗑️ Logs cleared", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadLogs() {
        lifecycleScope.launch {
            val logs = db.logDao().getAllLogs()
            if (logs.isNotEmpty()) {
                val formatted = logs.joinToString("\n") { formatLog(it) }
                binding.logsTextView.text = formatted
            } else {
                binding.logsTextView.text = "📁 No logs available"
            }
        }
    }

    private fun formatLog(log: ActivityLog): String {
        val date = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(log.timestamp))
        val emoji = when (log.type) {
            "HEARTBEAT" -> "❤️"
            "THREAT" -> "⚠️"
            "SCAN" -> "🔍"
            "LICENSE" -> "💳"
            "SYSTEM" -> "🔄"
            else -> "📌"
        }
        return "🕒 $date $emoji ${log.message}"
    }
}
