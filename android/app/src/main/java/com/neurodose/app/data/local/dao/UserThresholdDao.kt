package com.neurodose.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neurodose.app.data.local.entity.UserThresholdEntity
import kotlinx.coroutines.flow.Flow

/**
 * UserThresholdDao provides database access methods for UserThresholdEntity.
 * 
 * This DAO handles user-defined concentration thresholds for supplements,
 * which are used to trigger alerts when concentration goes outside safe ranges.
 */
@Dao
interface UserThresholdDao {
    
    /**
     * Insert a new threshold for a supplement.
     * 
     * @param threshold The threshold to insert
     * @return The row ID of the inserted threshold
     */
    @Insert
    suspend fun insert(threshold: UserThresholdEntity): Long
    
    /**
     * Update an existing threshold.
     * 
     * @param threshold The threshold with updated values
     */
    @Update
    suspend fun update(threshold: UserThresholdEntity)
    
    /**
     * Delete a threshold.
     * 
     * @param threshold The threshold to delete
     */
    @Delete
    suspend fun delete(threshold: UserThresholdEntity)
    
    /**
     * Get threshold for a specific supplement.
     * 
     * @param supplementId The supplement ID
     * @return The threshold for that supplement, or null if not set
     */
    @Query("SELECT * FROM user_thresholds WHERE supplementId = :supplementId")
    fun getThresholdForSupplement(supplementId: Int): Flow<UserThresholdEntity?>
    
    /**
     * Get all thresholds.
     * 
     * @return Flow of all thresholds
     */
    @Query("SELECT * FROM user_thresholds ORDER BY supplementId ASC")
    fun getAllThresholds(): Flow<List<UserThresholdEntity>>
    
    /**
     * Get all enabled thresholds (for alert checking).
     * 
     * @return Flow of all enabled thresholds
     */
    @Query("SELECT * FROM user_thresholds WHERE alertEnabled = 1 ORDER BY supplementId ASC")
    fun getEnabledThresholds(): Flow<List<UserThresholdEntity>>
}

