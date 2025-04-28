package com.example.classroom.data.students

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.classroom.data.StudentsWithAverageMark
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    // Inserts a new [Student] into the database
    @Insert
    suspend fun insert(student: Student)

    // Retrieves a student from the database based on their ID
    @Query("SELECT * FROM students WHERE studentId = :studentId")
    suspend fun getStudentById(studentId: String): Student

    // Retrieves all students form the database
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Student>>

    // Retrieves a list of students along with their average quiz marks
    @Query(
        "SELECT students.studentId, students.studentName, AVG(quiz_attempts.finalMark) " +
        "AS averageMark FROM students INNER JOIN quiz_attempts " +
        "ON students.studentId = quiz_attempts.studentId GROUP BY students.studentId"
    )
    fun getStudentsWithAverageMarks(): Flow<List<StudentsWithAverageMark>>
}