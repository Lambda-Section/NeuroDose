package com.neurodose.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neurodose.app.data.local.dao.SupplementDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabasePrepopulationTest {
    private lateinit var db: AppDatabase
    private lateinit var supplementDao: SupplementDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .addCallback(AppDatabase.Callback())
            .build()
        supplementDao = db.supplementDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testDatabasePrepopulation() = runBlocking {
        // Querying triggers onCreate
        val supplements = supplementDao.getAllSupplements().first()
        
        // Verify count (Caffeine, L-Theanine, Ginseng, Rhodiola)
        assertEquals(4, supplements.size)
        
        // Verify specific data
        val caffeine = supplements.find { it.name == "Caffeine" }
        assertNotNull(caffeine)
        assertEquals(5.0, caffeine?.halfLife ?: 0.0, 0.01)
        
        val lTheanine = supplements.find { it.name == "L-Theanine" }
        assertNotNull(lTheanine)
        assertEquals(1.0, lTheanine?.bioavailability ?: 0.0, 0.01)
    }
}
