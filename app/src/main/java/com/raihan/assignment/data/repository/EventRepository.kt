package com.raihan.assignment.data.repository

import com.raihan.assignment.data.source.local.database.dao.EventDao
import com.raihan.assignment.data.source.local.database.entity.EventEntity
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun getAllEvents(): Flow<List<EventEntity>> {
        return eventDao.getAllEvent()
    }

    suspend fun createEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }
}