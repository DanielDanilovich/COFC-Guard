package com.cofc.guard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cofc.guard.models.ActivityLog

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(log: ActivityLog)

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC")
    suspend fun getAllLogs(): List<ActivityLog>

    @Query("DELETE FROM activity_logs")
    suspend fun clearLogs()
}
