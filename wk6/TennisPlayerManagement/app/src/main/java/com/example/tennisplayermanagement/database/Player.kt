package com.example.tennisplayermanagement.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * * Represents a Player entity in the database
 */
@Entity(tableName = "players")
class Player (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val ranking: Int,
    val topVenue: String
)