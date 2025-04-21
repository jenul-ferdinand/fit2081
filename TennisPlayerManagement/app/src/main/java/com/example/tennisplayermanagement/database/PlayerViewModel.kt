package com.example.tennisplayermanagement.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlayerViewModel(context: Context): ViewModel() {
    private val playerRepository: PlayerRepository = PlayerRepository(context)

    val allPlayers: Flow<List<Player>> = playerRepository.getAllPlayers()

    fun insertPlayer(player: Player) = viewModelScope.launch {
        playerRepository.insertPlayer(player)
    }

    fun updatePlayer(player: Player) = viewModelScope.launch {
        playerRepository.updatePlayer(player)
    }

    fun deletePlayer(player: Player) = viewModelScope.launch {
        playerRepository.deletePlayer(player)
    }

    fun deleteAllPlayers() = viewModelScope.launch {
        playerRepository.deleteAllPlayers()
    }

    fun deletePlayerById(playerId: Int) = viewModelScope.launch {
        playerRepository.deletePlayerById(playerId)
    }

    class PlayerViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T = PlayerViewModel(context) as T
    }
}