package com.cofc.guard.models

data class ActivityLog(
    val id: Long,
    val timestamp: Long,
    val type: String,
    val message: String,
    val details: String? = null
)
