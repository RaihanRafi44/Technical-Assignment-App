package com.raihan.assignment.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey()
    var id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null
)
