package com.neurodose.app.utils.extension

import com.neurodose.app.data.local.entity.SupplementEntity
import com.neurodose.app.domain.model.Supplement

/**
 * Extension functions for converting between Entity and Domain models.
 * 
 * Extension functions allow us to add methods to existing classes
 * without inheritance. They're useful for converting between layers.
 */

/**
 * Convert SupplementEntity (database model) to Supplement (domain model).
 * 
 * @return The domain model
 */
fun SupplementEntity.toDomain(): Supplement {
    return Supplement(
        id = this.id,
        name = this.name,
        category = this.category,
        halfLife = this.halfLife,
        bioavailability = this.bioavailability,
        absorptionRate = this.absorptionRate,
        volumeOfDistribution = this.volumeOfDistribution,
        typicalDose = this.typicalDose,
        maxDailyDose = this.maxDailyDose,
        warnings = this.warnings
    )
}

/**
 * Convert Supplement (domain model) to SupplementEntity (database model).
 * 
 * @return The entity model
 */
fun Supplement.toEntity(): SupplementEntity {
    return SupplementEntity(
        id = this.id,
        name = this.name,
        category = this.category,
        halfLife = this.halfLife,
        bioavailability = this.bioavailability,
        absorptionRate = this.absorptionRate,
        volumeOfDistribution = this.volumeOfDistribution,
        typicalDose = this.typicalDose,
        maxDailyDose = this.maxDailyDose,
        warnings = this.warnings
    )
}

/**
 * Convert list of SupplementEntity to list of Supplement.
 * 
 * @return List of domain models
 */
fun List<SupplementEntity>.toDomain(): List<Supplement> {
    return this.map { it.toDomain() }
}

