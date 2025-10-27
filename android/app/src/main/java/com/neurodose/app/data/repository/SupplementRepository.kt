package com.neurodose.app.data.repository

import com.neurodose.app.data.local.dao.SupplementDao
import com.neurodose.app.data.local.entity.SupplementEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * SupplementRepository provides a clean API for accessing supplement data.
 * 
 * The Repository pattern abstracts the data layer from the rest of the app.
 * This allows us to:
 * - Change data sources (local, remote, cache) without affecting the rest of the app
 * - Provide a single source of truth for supplement data
 * - Make testing easier by mocking the repository
 * 
 * @Singleton ensures only one instance exists throughout the app's lifetime.
 * @Inject tells Hilt to inject the SupplementDao dependency.
 */
@Singleton
class SupplementRepository @Inject constructor(
    private val supplementDao: SupplementDao
) {
    
    /**
     * Get all supplements as a reactive Flow.
     * 
     * The UI automatically updates whenever supplements change.
     * 
     * @return Flow of all supplements
     */
    fun getAllSupplements(): Flow<List<SupplementEntity>> {
        return supplementDao.getAllSupplements()
    }
    
    /**
     * Get a single supplement by ID.
     * 
     * @param id The supplement ID
     * @return The supplement, or null if not found
     */
    suspend fun getSupplementById(id: Int): SupplementEntity? {
        return supplementDao.getSupplementById(id)
    }
    
    /**
     * Get supplements by category.
     * 
     * @param category The category name
     * @return Flow of supplements in that category
     */
    fun getSupplementsByCategory(category: String): Flow<List<SupplementEntity>> {
        return supplementDao.getSupplementsByCategory(category)
    }
    
    /**
     * Insert a new supplement.
     * 
     * @param supplement The supplement to insert
     * @return The row ID of the inserted supplement
     */
    suspend fun insertSupplement(supplement: SupplementEntity): Long {
        return supplementDao.insert(supplement)
    }
    
    /**
     * Insert multiple supplements at once.
     * 
     * Used for pre-populating the database with initial supplements.
     * 
     * @param supplements List of supplements to insert
     * @return List of row IDs
     */
    suspend fun insertSupplements(supplements: List<SupplementEntity>): List<Long> {
        return supplementDao.insertAll(supplements)
    }
    
    /**
     * Update an existing supplement.
     * 
     * @param supplement The supplement with updated values
     */
    suspend fun updateSupplement(supplement: SupplementEntity) {
        supplementDao.update(supplement)
    }
    
    /**
     * Delete a supplement.
     * 
     * @param supplement The supplement to delete
     */
    suspend fun deleteSupplement(supplement: SupplementEntity) {
        supplementDao.delete(supplement)
    }
    
    /**
     * Get the count of all supplements.
     * 
     * @return Number of supplements in database
     */
    suspend fun getSupplementCount(): Int {
        return supplementDao.getSupplementCount()
    }
}

