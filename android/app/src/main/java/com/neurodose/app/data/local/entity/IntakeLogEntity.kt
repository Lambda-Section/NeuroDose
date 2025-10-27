package com.neurodose.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * IntakeLogEntity represents a single supplement intake event.
 * 
 * This entity logs when a user takes a supplement and how much.
 * Multiple intake logs are used to calculate total concentration
 * using the superposition principle (sum of individual doses).
 * 
 * Fields:
 * - id: Unique identifier (auto-generated primary key)
 * - supplementId: Foreign key reference to SupplementEntity
 * - doseMg: Dose taken in milligrams
 * - timestamp: Unix timestamp (milliseconds) when supplement was taken
 * - notes: Optional user notes (e.g., "taken with food", "felt effect at 30min")
 * 
 * Foreign Key:
 * - supplementId references SupplementEntity.id
 * - If a supplement is deleted, all its intake logs are also deleted (CASCADE)
 */
@Entity(
    tableName = "intake_logs",
    foreignKeys = [
        ForeignKey(
            entity = SupplementEntity::class,
            parentColumns = ["id"],
            childColumns = ["supplementId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IntakeLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val supplementId: Int,
    val doseMg: Double,
    val timestamp: Long,
    val notes: String = ""
)

