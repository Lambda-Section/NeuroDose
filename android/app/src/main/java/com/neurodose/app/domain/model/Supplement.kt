package com.neurodose.app.domain.model

/**
 * Supplement is a domain model representing a supplement.
 * 
 * Domain models are independent of the database layer and represent
 * the business logic of the app. They're used throughout the UI and
 * business logic layers.
 * 
 * This is different from SupplementEntity which is tied to the database.
 * We can convert between them using extension functions.
 * 
 * Fields:
 * - id: Unique identifier
 * - name: Supplement name
 * - category: Category (Stimulant, Amino Acid, Adaptogen, Nootropic)
 * - halfLife: Half-life in hours
 * - bioavailability: Bioavailability (0.0 to 1.0)
 * - absorptionRate: Absorption rate constant (ka) in 1/hour
 * - volumeOfDistribution: Volume of distribution in L/kg
 * - typicalDose: Typical dose in mg
 * - maxDailyDose: Maximum recommended daily dose in mg
 * - warnings: Safety warnings and contraindications
 */
data class Supplement(
    val id: Int,
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

/**
 * Supplement categories for filtering and organization.
 */
object SupplementCategory {
    const val STIMULANT = "Stimulant"
    const val AMINO_ACID = "Amino Acid"
    const val ADAPTOGEN = "Adaptogen"
    const val NOOTROPIC = "Nootropic"
    
    fun getAllCategories(): List<String> = listOf(
        STIMULANT,
        AMINO_ACID,
        ADAPTOGEN,
        NOOTROPIC
    )
}

