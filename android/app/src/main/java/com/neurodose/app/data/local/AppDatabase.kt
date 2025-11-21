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

    companion object {
        class Callback : RoomDatabase.Callback() {
            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onCreate(db)
                
                // Caffeine
                db.execSQL("""
                    INSERT INTO supplements (name, category, halfLife, bioavailability, absorptionRate, volumeOfDistribution, typicalDose, maxDailyDose, warnings)
                    VALUES ('Caffeine', 'Stimulant', 5.0, 0.99, 0.42, 0.6, 100.0, 400.0, 'May cause jitters, insomnia, anxiety. Avoid late in the day.')
                """.trimIndent())

                // L-Theanine
                db.execSQL("""
                    INSERT INTO supplements (name, category, halfLife, bioavailability, absorptionRate, volumeOfDistribution, typicalDose, maxDailyDose, warnings)
                    VALUES ('L-Theanine', 'Amino Acid', 3.0, 1.0, 0.69, 0.7, 200.0, 1200.0, 'Generally safe. May lower blood pressure.')
                """.trimIndent())

                // Ginseng
                db.execSQL("""
                    INSERT INTO supplements (name, category, halfLife, bioavailability, absorptionRate, volumeOfDistribution, typicalDose, maxDailyDose, warnings)
                    VALUES ('Ginseng', 'Adaptogen', 8.0, 0.16, 0.26, 1.2, 200.0, 2000.0, 'May interact with blood thinners and diabetes medications.')
                """.trimIndent())

                // Rhodiola
                db.execSQL("""
                    INSERT INTO supplements (name, category, halfLife, bioavailability, absorptionRate, volumeOfDistribution, typicalDose, maxDailyDose, warnings)
                    VALUES ('Rhodiola', 'Adaptogen', 4.0, 0.52, 0.52, 0.8, 300.0, 600.0, 'Avoid if bipolar. May cause dizziness or dry mouth.')
                """.trimIndent())
            }
        }
    }
}

