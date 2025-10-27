package com.neurodose.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.neurodose.app.data.local.entity.IntakeLogEntity
import kotlinx.coroutines.flow.Flow

/**
 * IntakeLogDao provides database access methods for IntakeLogEntity.
 * 
 * This DAO handles all operations related to supplement intake logs,
 * including inserting new intakes, querying historical data, and
 * calculating statistics for pharmacokinetic modeling.
 */
@Dao
interface IntakeLogDao {
    
    /**
     * Insert a new intake log entry.
     * 
     * @param intakeLog The intake log to insert
     * @return The row ID of the inserted log
     */
    @Insert
    suspend fun insert(intakeLog: IntakeLogEntity): Long
    
    /**
     * Delete an intake log entry.
     * 
     * @param intakeLog The intake log to delete
     */
    @Delete
    suspend fun delete(intakeLog: IntakeLogEntity)
    
    /**
     * Get all intake logs for a specific supplement.
     * 
     * Used for calculating current concentration using superposition principle.
     * 
     * @param supplementId The supplement ID
     * @return Flow of all intake logs for that supplement
     */
    @Query("SELECT * FROM intake_logs WHERE supplementId = :supplementId ORDER BY timestamp DESC")
    fun getIntakeLogsBySupplementId(supplementId: Int): Flow<List<IntakeLogEntity>>
    
    /**
     * Get intake logs for a supplement within a time range.
     * 
     * Used for calculating concentration at specific time periods.
     * 
     * @param supplementId The supplement ID
     * @param startTime Start timestamp (milliseconds)
     * @param endTime End timestamp (milliseconds)
     * @return Flow of intake logs within the time range
     */
    @Query("""
        SELECT * FROM intake_logs 
        WHERE supplementId = :supplementId 
        AND timestamp BETWEEN :startTime AND :endTime 
        ORDER BY timestamp DESC
    """)
    fun getIntakeLogsByTimeRange(
        supplementId: Int,
        startTime: Long,
        endTime: Long
    ): Flow<List<IntakeLogEntity>>
    
    /**
     * Get the most recent intake log for a supplement.
     * 
     * @param supplementId The supplement ID
     * @return The most recent intake log, or null if none exist
     */
    @Query("""
        SELECT * FROM intake_logs 
        WHERE supplementId = :supplementId 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
    suspend fun getMostRecentIntakeLog(supplementId: Int): IntakeLogEntity?
    
    /**
     * Get all intake logs across all supplements.
     * 
     * @return Flow of all intake logs
     */
    @Query("SELECT * FROM intake_logs ORDER BY timestamp DESC")
    fun getAllIntakeLogs(): Flow<List<IntakeLogEntity>>
    
    /**
     * Delete all intake logs for a specific supplement.
     * 
     * @param supplementId The supplement ID
     */
    @Query("DELETE FROM intake_logs WHERE supplementId = :supplementId")
    suspend fun deleteAllLogsForSupplement(supplementId: Int)
}

