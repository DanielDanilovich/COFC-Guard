package com.cofc.guard.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

object LicenseUtils {
    private const val PREF_NAME = "cofc_guard_license"
    private const val KEY_VALID = "license_valid"
    private const val KEY_EXPIRY = "license_expiry"
    private const val KEY_TRIAL_START = "trial_start"
    private const val TRIAL_DAYS = 3

    fun isTrialActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val trialStart = prefs.getLong(KEY_TRIAL_START, 0)
        
        if (trialStart == 0L) {
            prefs.edit().putLong(KEY_TRIAL_START, System.currentTimeMillis()).apply()
            return true
        }
        
        val daysPassed = (System.currentTimeMillis() - trialStart) / (1000 * 60 * 60 * 24)
        return daysPassed < TRIAL_DAYS
    }

    fun hasValidLicense(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isValid = prefs.getBoolean(KEY_VALID, false)
        if (!isValid) return isTrialActive(context)
        
        val expiry = prefs.getLong(KEY_EXPIRY, 0)
        if (expiry == 0L) return true
        return Date().time < expiry
    }

    fun activateLicense(context: Context, licenseKey: String, plan: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_VALID, true)
            val expiry = when (plan) {
                "monthly" -> System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)
                "yearly" -> System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)
                else -> 0L
            }
            putLong(KEY_EXPIRY, expiry)
            apply()
        }
    }
}
