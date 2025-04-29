package com.example.classroom.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.classroom.data.quizzes.QuizAttemptDao
import com.example.classroom.data.students.StudentDao

abstract class CollegeDatabase: RoomDatabase() {
    // Provides access to the StudentDao interface for performing
    // database operations on Student entities.
    abstract fun studentDao(): StudentDao

    // Provides access to the QuizAttemptDao interface for performing
    // database operations on QuizAttempt entities.
    abstract fun quizAttemptDao(): QuizAttemptDao

    companion object {
        @Volatile
        private var INST: CollegeDatabase? = null

        fun getDatabase(context: Context): CollegeDatabase {
            return INST ?: synchronized(this) {
                Room.databaseBuilder(context, CollegeDatabase::class.java, "item_database")
                    .build()
                    .also { INST = it }
            }
        }
    }
}