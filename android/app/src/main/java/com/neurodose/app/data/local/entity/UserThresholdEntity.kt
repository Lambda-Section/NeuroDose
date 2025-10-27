package com.neurodose.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * UserThresholdEntity stores user-defined concentration thresholds for supplements.
 * 
 * Users can set minimum and maximum concentration thresholds for each supplement.
 * The app will alert the user when concentration goes below minimum or above maximum.
 * 
 * Fields:
 * - id: Unique identifier (auto-generated primary key)
 * - supplementId: Foreign key reference to SupplementEntity
 * - minThreshold: Minimum desired concentration in mg/L
 * - maxThreshold: Maximum safe concentration in mg/L
 * - alertEnabled: Whether to show alerts for this supplement
 * 
 * Foreign Key:
 * - supplementId references SupplementEntity.id
 * - If a supplement is deleted, its threshold is also deleted (CASCADE)
 * 
 * Example:
 * - Caffeine: minThreshold = 0.5 mg/L, maxThreshold = 2.0 mg/L
 * - L-Theanine: minThreshold = 0.3 mg/L, maxThreshold = 1.5 mg/L
 */
@Entity(
    tableName = "user_thresholds",
    foreignKeys = [
        ForeignKey(
            entity = SupplementEntity::class,
            parentColumns = ["id"],
            childColumns = ["supplementId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserThresholdEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val supplementId: Int,
    val minThreshold: Double,
    val maxThreshold: Double,
    val alertEnabled: Boolean = true
)

