package com.neurodose.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neurodose.app.data.local.entity.SupplementEntity
import kotlinx.coroutines.flow.Flow

/**
 * SupplementDao provides database access methods for SupplementEntity.
 * 
 * DAOs (Data Access Objects) are the main components of Room that define
 * the database operations. Room generates the implementation at compile time.
 * 
 * All methods are suspend functions (can be called from coroutines) for
 * non-blocking database operations.
 * 
 * Query methods return Flow<T> for reactive updates - the UI automatically
 * updates when data changes in the database.
 */
@Dao
interface SupplementDao {
    
    /**
     * Insert a new supplement into the database.
     * 
     * @param supplement The supplement to insert
     * @return The row ID of the inserted supplement
     */
    @Insert
    suspend fun insert(supplement: SupplementEntity): Long
    
    /**
     * Insert multiple supplements at once.
     * 
     * @param supplements List of supplements to insert
     * @return List of row IDs
     */
    @Insert
    suspend fun insertAll(supplements: List<SupplementEntity>): List<Long>
    
    /**
     * Update an existing supplement.
     * 
     * @param supplement The supplement with updated values
     */
    @Update
    suspend fun update(supplement: SupplementEntity)
    
    /**
     * Delete a supplement.
     * 
     * @param supplement The supplement to delete
     */
    @Delete
    suspend fun delete(supplement: SupplementEntity)
    
    /**
     * Get all supplements as a reactive Flow.
     * 
     * The UI will automatically update whenever supplements change.
     * 
     * @return Flow of all supplements
     */
    @Query("SELECT * FROM supplements ORDER BY name ASC")
    fun getAllSupplements(): Flow<List<SupplementEntity>>
    
    /**
     * Get a single supplement by ID.
     * 
     * @param id The supplement ID
     * @return The supplement, or null if not found
     */
    @Query("SELECT * FROM supplements WHERE id = :id")
    suspend fun getSupplementById(id: Int): SupplementEntity?
    
    /**
     * Get supplements by category.
     * 
     * @param category The category name
     * @return Flow of supplements in that category
     */
    @Query("SELECT * FROM supplements WHERE category = :category ORDER BY name ASC")
    fun getSupplementsByCategory(category: String): Flow<List<SupplementEntity>>
    
    /**
     * Get the count of all supplements.
     * 
     * @return Number of supplements in database
     */
    @Query("SELECT COUNT(*) FROM supplements")
    suspend fun getSupplementCount(): Int
}

