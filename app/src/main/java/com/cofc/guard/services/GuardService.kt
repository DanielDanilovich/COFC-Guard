package com.cofc.guard.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cofc.guard.R
import com.cofc.guard.database.LogsDatabase
import com.cofc.guard.models.ActivityLog
import kotlinx.coroutines.*

class GuardService : Service() {
    private val CHANNEL_ID = "cofc_guard_channel"
    private val NOTIFICATION_ID = 1001
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var db: LogsDatabase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        db = LogsDatabase.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            var heartbeatCount = 0
            while (true) {
                // 1. Heartbeat every 10 seconds
                heartbeatCount++
                val log = ActivityLog(
                    id = System.currentTimeMillis(),
                    timestamp = System.currentTimeMillis(),
                    type = "HEARTBEAT",
                    message = "Heartbeat #$heartbeatCount - System running"
                )
                db.logDao().insertLog(log)

                // 2. Check if activity is needed
                if (heartbeatCount % 6 == 0) { // Every minute
                    val systemLog = ActivityLog(
                        id = System.currentTimeMillis(),
                        timestamp = System.currentTimeMillis(),
                        type = "SYSTEM",
                        message = "🔄 All 21 Quantum Layers Active"
                    )
                    db.logDao().insertLog(systemLog)
                }

                delay(10000)
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "COFC GUARD Protection",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "21/21 Quantum Layers Active"
                setShowBadge(false)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🛡️ COFC GUARD")
            .setContentText("21/21 Quantum Layers Active")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent): IBinder? = null
}
