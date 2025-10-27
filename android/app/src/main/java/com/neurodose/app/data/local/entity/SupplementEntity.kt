package com.neurodose.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SupplementEntity represents a supplement in the database.
 * 
 * This entity stores all pharmacokinetic parameters needed to calculate
 * supplement concentration over time using the one-compartment model.
 * 
 * Fields:
 * - id: Unique identifier (auto-generated primary key)
 * - name: Supplement name (e.g., "Caffeine", "L-Theanine")
 * - category: Category (e.g., "Stimulant", "Amino Acid", "Adaptogen", "Nootropic")
 * - halfLife: Half-life in hours (time for concentration to reduce by 50%)
 * - bioavailability: Bioavailability as decimal (0.0 to 1.0)
 * - absorptionRate: Absorption rate constant (ka) in 1/hour
 * - volumeOfDistribution: Volume of distribution in L/kg
 * - typicalDose: Typical dose in mg
 * - maxDailyDose: Maximum recommended daily dose in mg
 * - warnings: Safety warnings and contraindications
 */
@Entity(tableName = "supplements")
data class SupplementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val halfLife: Double,
    val bioavailability: Double,
    val absorptionRate: Double,
    val volumeOfDistribution: Double,
    val typicalDose: Double,
    val maxDailyDose: Double,
    val warnings: String
)

