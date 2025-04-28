package com.example.classroom.data.quizzes

import android.content.Context
import com.example.classroom.data.CollegeDatabase
import kotlinx.coroutines.flow.Flow

class QuizAttemptsRepository(context: Context) {
    // Create an instance of the QuizAttempt DAO
    private val quizAttemptDao = CollegeDatabase.getDatabase(context).quizAttemptDao()

    // Retrieve all quiz attempts from the database
    val allAttempts: Flow<List<QuizAttempt>> = quizAttemptDao.getAllQuizAttempts()

    // Insert a new quiz attempt into the database
    suspend fun insert(attempt: QuizAttempt) {
        quizAttemptDao.insert(attempt)
    }

    // Retrieve quiz attempts for a specific student from the database
    fun getQuizAttemptByStudentId(studentId: String): Flow<List<QuizAttempt>> =
        quizAttemptDao.getQuizAttemptByStudentId(studentId)
}