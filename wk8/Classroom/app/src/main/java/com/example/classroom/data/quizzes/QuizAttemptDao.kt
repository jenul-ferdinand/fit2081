package com.example.classroom.data.quizzes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {
    // Inserts a new [QuizAttempt] into the database
    @Insert
    suspend fun insert(quizAttempt: QuizAttempt)

    // Retrieves all quiz attempts from the database as a [Flow] of lists
    @Query("SELECT * FROM quiz_attempts")
    fun getAllQuizAttempts(): Flow<List<QuizAttempt>>

    // Retrieves all quiz attempts for a specific student ID
    @Query("SELECT * FROM quiz_attempts WHERE studentId = :studentId")
    fun getQuizAttemptByStudentId(studentId: String): Flow<List<QuizAttempt>>
}