package com.cofc.guard.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cofc.guard.R
import com.cofc.guard.databinding.ActivityLogsBinding
import com.cofc.guard.models.LogEntry

class LogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogsBinding
    private lateinit var adapter: LogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        adapter = LogAdapter()
        binding.logsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.logsRecyclerView.adapter = adapter

        // Load logs
        loadLogs()
    }

    private fun loadLogs() {
        // טעינת לוגים ממסד נתונים
        val logs = listOf(
            LogEntry(1, System.currentTimeMillis(), "HEARTBEAT", "System running", null),
            LogEntry(2, System.currentTimeMillis() - 10000, "HEARTBEAT", "System running", null)
        )
        adapter.submitList(logs)
    }
}
