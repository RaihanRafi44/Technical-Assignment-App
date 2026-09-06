package com.raihan.assignment.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raihan.assignment.data.source.local.database.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAllEvent(): Flow<List<EventEntity>>

    @Insert
    suspend fun insertEvent(schedule: EventEntity): Long

    @Update
    suspend fun updateEvent(schedule: EventEntity): Int

    @Delete
    suspend fun deleteEvent(schedule: EventEntity): Int

    @Query("DELETE FROM event")
    suspend fun deleteAll()
}