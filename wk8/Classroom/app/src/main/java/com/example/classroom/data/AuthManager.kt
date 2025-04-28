package com.example.classroom.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AuthManager {
    val _userId: MutableState<String?> = mutableStateOf(null)

    fun login(userId: String) {
        _userId.value = userId
    }

    fun logout() {
        _userId.value = null
    }

    fun getStudentId(): String? {
        return _userId.value
    }
}