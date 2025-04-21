package com.example.tennisplayermanagement.database

import android.content.Context
import kotlinx.coroutines.flow.Flow

class PlayerRepository {
    var playerDAO: PlayerDAO

    constructor(context: Context) {
        playerDAO = PlayerDatabase.getDatabase(context).playerDAO()
    }

    suspend fun insertPlayer(player: Player) {
        playerDAO.insertPlayer(player)
    }

    suspend fun updatePlayer(player: Player) {
        playerDAO.updatePlayer(player)
    }

    suspend fun deletePlayer(player: Player) {
        playerDAO.deletePlayer(player)
    }

    suspend fun deleteAllPlayers() {
        playerDAO.deleteAllPlayers()
    }

    suspend fun deletePlayerById(playerId: Int) {
        playerDAO.deletePlayerById(playerId)
    }

    fun getAllPlayers(): Flow<List<Player>> = playerDAO.getAllPlayers()
}