package com.rivaldo.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name="photoUrl")
    val photoUrl: String? = null,
    @ColumnInfo(name="createdAt")
    val createdAt: String? = null,
    @ColumnInfo(name="description")
    val description: String? = null,
    @ColumnInfo(name="lon")
    val lon: Float? = null,
    @ColumnInfo(name="lat")
    val lat: Float? = null
) : Serializable
