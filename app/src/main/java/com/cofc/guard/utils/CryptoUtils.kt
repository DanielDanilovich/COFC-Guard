package com.cofc.guard.utils

import java.security.MessageDigest

object CryptoUtils {
    fun sha3_512(input: String): String {
        val digest = MessageDigest.getInstance("SHA3-512")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun validateLicense(license: String): Boolean {
        return license.startsWith("COFC-") && license.length >= 20
    }

    fun generateDeviceFingerprint(): String {
        val data = "${System.currentTimeMillis()}-${Math.random()}"
        return sha3_512(data)
    }
}
