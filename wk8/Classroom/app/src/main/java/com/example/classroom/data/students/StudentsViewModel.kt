package com.example.classroom.data.students

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.classroom.data.StudentsWithAverageMark
import kotlinx.coroutines.flow.Flow

class StudentsViewModel(context: Context): ViewModel() {
    val repository = StudentsRepository(context = context)

    // Retrieves a Flow of all students
    val allStudents: Flow<List<Student>> = repository.getAllStudents()

    // Inserts a new student into the database
    suspend fun insert(student: Student) = repository.insertStudent(student)

    // Retrieves a student by their ID
    suspend fun getStudentById(studentId: String): Student {
        return repository.getStudentById(studentId)
    }

    // Retrieves a Flow of students with their average marks
    fun getStudentsWithAverageMarks(): Flow<List<StudentsWithAverageMark>> =
        repository.getStudentsWithAverageMarks()

    // Factory class for creating instances of StudentsViewModel
    class StudentsViewModelFactory(context: Context): ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T: ViewModel> create(modelClass: Class<T>): T =
            StudentsViewModel(context) as T
    }
}