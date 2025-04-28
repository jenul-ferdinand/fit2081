package com.example.classroom.data.students

import android.content.Context
import com.example.classroom.data.CollegeDatabase
import com.example.classroom.data.StudentsWithAverageMark
import kotlinx.coroutines.flow.Flow

class StudentsRepository(context: Context) {
    // Get the Student DAO instance from the database
    private val studentDao = CollegeDatabase.getDatabase(context).studentDao()

    // Inserts a new student into the database
    suspend fun insertStudent(student: Student) {
        studentDao.insert(student)
    }

    // Retrieves all students from the database by their ID
    suspend fun getStudentById(studentId: String): Student {
        return studentDao.getStudentById(studentId)
    }

    // Retrieves all students from the database as a Flow.
    // This allows observing changes to the student list over time.
    fun getAllStudents(): Flow<List<Student>> = studentDao.getAll()

    // Retrieves students along with their average marks as a Flow
    // This allows observing changes to the student list with their average marks over time.
    fun getStudentsWithAverageMarks(): Flow<List<StudentsWithAverageMark>> =
        studentDao.getStudentsWithAverageMarks()
}