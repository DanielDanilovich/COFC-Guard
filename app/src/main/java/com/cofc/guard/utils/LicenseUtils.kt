package com.cofc.guard.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

object LicenseUtils {
    private const val PREF_NAME = "cofc_guard_license"
    private const val KEY_LICENSE = "license_key"
    private const val KEY_VALID = "license_valid"
    private const val KEY_EXPIRY = "license_expiry"

    fun hasValidLicense(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isValid = prefs.getBoolean(KEY_VALID, false)
        val expiry = prefs.getLong(KEY_EXPIRY, 0)
        
        if (!isValid) return false
        if (expiry == 0L) return true // Lifetime
        
        return Date().time < expiry
    }

    fun saveLicense(context: Context, licenseKey: String, plan: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_LICENSE, licenseKey)
            putBoolean(KEY_VALID, true)
            
            // Set expiry based on plan
            val expiry = when (plan) {
                "monthly" -> System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)
                "yearly" -> System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)
                else -> 0L // Lifetime
            }
            putLong(KEY_EXPIRY, expiry)
            apply()
        }
    }

    fun clearLicense(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun getLicenseStatus(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isValid = prefs.getBoolean(KEY_VALID, false)
        val expiry = prefs.getLong(KEY_EXPIRY, 0)
        
        if (!isValid) return "Invalid"
        if (expiry == 0L) return "Lifetime"
        if (Date().time > expiry) return "Expired"
        
        return "Active"
    }
}
