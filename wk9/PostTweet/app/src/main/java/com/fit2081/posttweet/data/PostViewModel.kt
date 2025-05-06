package com.fit2081.posttweet.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repo: PostRepo = PostRepo(application.applicationContext)

    private val _allPosts = MutableStateFlow<List<Post>>(emptyList())

    val allPosts: StateFlow<List<Post>> get() = _allPosts.asStateFlow()

    init {
        refreshPosts()
    }

    fun generateRandomPost() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createPost()
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _allPosts.value = repo.getAllPosts()
            println("Posts refreshsed: ${_allPosts.value.size} posts loaded")
        }
    }

    fun isNetworkAvailable(): Boolean {
        return repo.isNetworkAvailable()
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            repo.deleteAllPosts()
            refreshPosts()
        }
    }
}