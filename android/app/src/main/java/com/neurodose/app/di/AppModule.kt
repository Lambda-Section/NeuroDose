package com.neurodose.app.di

import android.content.Context
import androidx.room.Room
import com.neurodose.app.data.local.AppDatabase
import com.neurodose.app.data.local.dao.SupplementDao
import com.neurodose.app.data.local.dao.IntakeLogDao
import com.neurodose.app.data.local.dao.UserThresholdDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule provides application-level dependencies using Hilt.
 * 
 * Hilt is a dependency injection framework built on top of Dagger.
 * It simplifies dependency injection by automatically generating code
 * to provide dependencies throughout the app.
 * 
 * @Module tells Hilt this class provides dependencies.
 * @InstallIn(SingletonComponent::class) means these dependencies live
 * for the entire app's lifetime (not recreated for each screen).
 * 
 * @Provides methods tell Hilt how to create instances of dependencies.
 * @Singleton ensures only one instance is created and reused.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provide the Room database instance.
     * 
     * Room.databaseBuilder creates a new database or opens an existing one.
     * The database file is stored in the app's private directory.
     * 
     * @param context Application context
     * @return The AppDatabase instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "neurodose_database"
        )
            .fallbackToDestructiveMigration() // For development only
            .build()
    }
    
    /**
     * Provide the SupplementDao.
     * 
     * @param database The AppDatabase instance
     * @return The SupplementDao
     */
    @Provides
    @Singleton
    fun provideSupplementDao(database: AppDatabase): SupplementDao {
        return database.supplementDao()
    }
    
    /**
     * Provide the IntakeLogDao.
     * 
     * @param database The AppDatabase instance
     * @return The IntakeLogDao
     */
    @Provides
    @Singleton
    fun provideIntakeLogDao(database: AppDatabase): IntakeLogDao {
        return database.intakeLogDao()
    }
    
    /**
     * Provide the UserThresholdDao.
     * 
     * @param database The AppDatabase instance
     * @return The UserThresholdDao
     */
    @Provides
    @Singleton
    fun provideUserThresholdDao(database: AppDatabase): UserThresholdDao {
        return database.userThresholdDao()
    }
}

