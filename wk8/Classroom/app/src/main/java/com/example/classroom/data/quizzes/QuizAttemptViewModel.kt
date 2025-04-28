package com.example.classroom.data.quizzes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuizAttemptViewModel(context: Context) : ViewModel() {
    private val quizAttemptsRepository: QuizAttemptsRepository =
        QuizAttemptsRepository(context)

    // Flow of all quiz attempts
    val allAttempts: Flow<List<QuizAttempt>> = quizAttemptsRepository.allAttempts

    // Inserts a new quiz attempt into the database
    fun insertQuizAttempt(quizAttempt: QuizAttempt) {
        viewModelScope.launch {
            quizAttemptsRepository.insert(quizAttempt)
        }
    }

    // Retrieves quiz attempts for a specific student ID
    fun getQuizAttemptByStudentId(studentId: String): Flow<List<QuizAttempt>> =
        quizAttemptsRepository.getQuizAttemptByStudentId(studentId)

    class QuizAttemptViewModelFactory(context: Context): ViewModelProvider.Factory {
        private val context = context.applicationContext

        // Use application context to avoid memory leaks
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            QuizAttemptViewModel(context) as T
    }
}