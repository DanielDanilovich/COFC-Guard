package com.cofc.guard.utils

import android.content.Context
import android.content.SharedPreferences

object LicenseUtils {
    private const val PREF_NAME = "cofc_guard_license"
    private const val KEY_LICENSE = "license_key"
    private const val KEY_VALID = "license_valid"

    fun hasValidLicense(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_VALID, false)
    }

    fun saveLicense(context: Context, licenseKey: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_LICENSE, licenseKey)
            putBoolean(KEY_VALID, true)
            apply()
        }
    }
}
