package com.raihan.assignment.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "start_date")
    var startDate: String,
    @ColumnInfo(name = "start_time")
    var startTime: String,
    @ColumnInfo(name = "end_date")
    var endDate: String,
    @ColumnInfo(name = "end_time")
    var endTime: String,
    @ColumnInfo(name = "organizer")
    var organizer: String,
    @ColumnInfo(name = "thumbnail_uri")
    var thumbnailUri: String,
    @ColumnInfo(name = "start_timestamp")
    var startTimestamp: Long // Digunakan untuk mencari event terdekat
)
