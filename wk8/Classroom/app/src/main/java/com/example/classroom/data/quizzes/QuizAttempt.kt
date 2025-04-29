package com.example.classroom.data.quizzes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttempt (
    // Unique ID for the quiz attempt
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // ID of the student who attempted the quiz
    val studentId: String,

    // ID of the quiz that was attempted
    val quizId: String,

    val quizDate: String,
    val finalMark: Double
)