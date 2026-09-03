package com.cofc.guard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cofc.guard.models.ActivityLog

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(log: ActivityLog)

    @Query("SELECT * FROM ActivityLog ORDER BY timestamp DESC")
    suspend fun getAllLogs(): List<ActivityLog>

    @Query("DELETE FROM ActivityLog")
    suspend fun clearLogs()
}
