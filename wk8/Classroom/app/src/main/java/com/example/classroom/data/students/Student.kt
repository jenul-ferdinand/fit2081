package com.example.classroom.data.students

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentName: String,
    val studentPassword: String,
    val studentId: String = ""
)