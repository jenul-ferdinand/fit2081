package com.example.tennisplayermanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Player::class], version = 1, exportSchema = false)
abstract class PlayerDatabase: RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO

    companion object {
        const val DATABASE_NAME = "player_database"

        // Singleton instance of the database
        @Volatile
        private var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, PlayerDatabase::class.java, DATABASE_NAME)
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}