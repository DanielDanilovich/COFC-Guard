package com.cofc.guard.utils

import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"
    private const val KEY_SIZE = 256

    private var secretKey: SecretKey? = null

    fun generateKey(): String {
        val keyGen = KeyGenerator.getInstance(ALGORITHM)
        keyGen.init(KEY_SIZE)
        secretKey = keyGen.generateKey()
        return Base64.getEncoder().encodeToString(secretKey?.encoded ?: ByteArray(0))
    }

    fun encryptData(data: String): String {
        val key = secretKey ?: run {
            generateKey()
            secretKey
        }
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decryptData(encryptedData: String): String {
        val key = secretKey ?: return ""
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decrypted)
    }

    fun sha3_512(input: String): String {
        val digest = MessageDigest.getInstance("SHA3-512")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun generateHash(input: String): String {
        return sha3_512(input)
    }

    fun validateLicense(license: String): Boolean {
        return license.startsWith("COFC-") && license.length >= 20
    }
}
