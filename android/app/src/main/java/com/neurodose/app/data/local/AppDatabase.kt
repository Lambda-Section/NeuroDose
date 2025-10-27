package com.neurodose.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neurodose.app.data.local.entity.SupplementEntity
import com.neurodose.app.data.local.entity.IntakeLogEntity
import com.neurodose.app.data.local.entity.UserThresholdEntity
import com.neurodose.app.data.local.dao.SupplementDao
import com.neurodose.app.data.local.dao.IntakeLogDao
import com.neurodose.app.data.local.dao.UserThresholdDao

/**
 * AppDatabase is the main Room database for NeuroDose.
 * 
 * Room is a type-safe wrapper around SQLite that provides:
 * - Compile-time verification of SQL queries
 * - Automatic conversion between Kotlin objects and database rows
 * - Coroutine support for non-blocking database operations
 * 
 * Entities:
 * - SupplementEntity: Stores supplement information (name, half-life, bioavailability, etc.)
 * - IntakeLogEntity: Stores user's supplement intake history
 * - UserThresholdEntity: Stores user's custom concentration thresholds
 * 
 * DAOs (Data Access Objects):
 * - SupplementDao: Provides methods to query and modify supplements
 * - IntakeLogDao: Provides methods to query and modify intake logs
 * - UserThresholdDao: Provides methods to query and modify thresholds
 */
@Database(
    entities = [
        SupplementEntity::class,
        IntakeLogEntity::class,
        UserThresholdEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Abstract methods that return DAOs
    // Room will implement these automatically
    abstract fun supplementDao(): SupplementDao
    abstract fun intakeLogDao(): IntakeLogDao
    abstract fun userThresholdDao(): UserThresholdDao
}

