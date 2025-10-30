package com.neurodose.app.domain.usecase

import com.neurodose.app.data.local.entity.SupplementEntity
import com.neurodose.app.data.repository.SupplementRepository
import javax.inject.Inject

/**
 * AddSupplementUseCase adds a new supplement to the database.
 * 
 * This use case:
 * - Takes supplement data as input
 * - Validates the data (can be extended)
 * - Inserts into the repository
 * - Returns the inserted supplement ID
 * 
 * @param repository The supplement repository
 */
class AddSupplementUseCase @Inject constructor(
    private val repository: SupplementRepository
) {
    
    /**
     * Execute the use case to add a supplement.
     * 
     * @param supplement The supplement to add
     * @return The row ID of the inserted supplement
     */
    suspend operator fun invoke(supplement: SupplementEntity): Long {
        return repository.insertSupplement(supplement)
    }
}

