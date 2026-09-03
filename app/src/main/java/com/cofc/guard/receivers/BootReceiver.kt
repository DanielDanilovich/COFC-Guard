package com.cofc.guard.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cofc.guard.services.GuardService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, GuardService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}
