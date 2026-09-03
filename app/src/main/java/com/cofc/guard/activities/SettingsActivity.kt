package com.cofc.guard.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toggle switches
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.autoStartSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Auto-Start: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.backgroundSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Background Service: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.exportLogsButton.setOnClickListener {
            Toast.makeText(this, "📤 Logs exported!", Toast.LENGTH_SHORT).show()
        }

        binding.clearDataButton.setOnClickListener {
            Toast.makeText(this, "🗑️ Data cleared!", Toast.LENGTH_SHORT).show()
        }
    }
}
