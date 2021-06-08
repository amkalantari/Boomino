package com.core.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.core.dto.TestDto

/**
 * Main database description.
 */
@Database(
    entities = [
        TestDto::class
    ],
    version = 1, exportSchema = false
)
@TypeConverters(OperatorTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @VisibleForTesting
        private const val DATABASE_NAME = "cache.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context, testMode: Boolean = false): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, testMode).also {
                    INSTANCE = it
                }
            }
        }

        @JvmStatic
        private fun buildDatabase(context: Context, testMode: Boolean): AppDatabase {
            return if (testMode) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    abstract fun testDao(): TestDao

}
