package com.example.tennisplayermanagement.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Every database operation is defined in this interface
 *
 * CRUD Operations
 */
@Dao
interface PlayerDAO {
    // Insert a new player
    @Insert
    suspend fun insertPlayer(player: Player)

    // Update player
    @Update
    suspend fun updatePlayer(player: Player)

    // Delete player
    @Delete
    suspend fun deletePlayer(player: Player)

    // Delete all players
    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()

    // Delete player by id
    @Query("DELETE FROM players WHERE id = :playerId")
    suspend fun deletePlayerById(playerId: Int)

    // Get all players
    @Query("SELECT * FROM players ORDER BY id ASC")
    fun getAllPlayers(): Flow<List<Player>>

}