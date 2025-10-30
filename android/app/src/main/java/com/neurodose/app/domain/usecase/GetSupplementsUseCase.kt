package com.neurodose.app.domain.usecase

import com.neurodose.app.data.local.entity.SupplementEntity
import com.neurodose.app.data.repository.SupplementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetSupplementsUseCase retrieves all supplements from the repository.
 * 
 * Use cases encapsulate business logic and are used by ViewModels.
 * They provide a clean interface between the UI and data layers.
 * 
 * This use case:
 * - Gets all supplements from the repository
 * - Returns a Flow for reactive updates
 * - Can be easily tested by mocking the repository
 * 
 * @param repository The supplement repository
 */
class GetSupplementsUseCase @Inject constructor(
    private val repository: SupplementRepository
) {
    
    /**
     * Execute the use case to get all supplements.
     * 
     * @return Flow of all supplements
     */
    operator fun invoke(): Flow<List<SupplementEntity>> {
        return repository.getAllSupplements()
    }
}

