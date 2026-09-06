package com.raihan.assignment.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raihan.assignment.data.source.local.database.dao.EventDao
import com.raihan.assignment.data.source.local.database.entity.EventEntity


@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun createInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AssignApp.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}