package com.fit2081.posttweet.data

import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile private var INST: AppDatabase ? = null

        fun getDatabase(context: Context): AppDatabase {
            return INST ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tweets_database"
                ).build()

                INST = instance
                instance
            }
        }
    }
}