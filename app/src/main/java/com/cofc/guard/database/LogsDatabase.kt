package com.cofc.guard.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [ActivityLog::class], version = 1)
abstract class LogsDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao

    companion object {
        @Volatile
        private var INSTANCE: LogsDatabase? = null

        fun getInstance(context: Context): LogsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LogsDatabase::class.java,
                    "cofc_guard_logs"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
