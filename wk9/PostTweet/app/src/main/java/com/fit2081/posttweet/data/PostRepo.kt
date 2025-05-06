package com.fit2081.posttweet.data

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import retrofit2.converter.gson.GsonConverterFactory

class PostRepo(context: Context) {
    private val postDao = AppDatabase.getDatabase(context.applicationContext).postDao()
    private val applicationContext = context.applicationContext

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://34.129.121.193:3000/") // Ensure this URL is correct and accessible
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(TweetApiService::class.java)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun getAllPosts(): List<Post> {
        return try {
            if (isNetworkAvailable()) {
                try {
                    // Fetch the API if online
                    val apiPosts = withContext(Dispatchers.IO) {
                        apiService.getTweets()
                    }

                    // Map API response to local Post entity
                    val posts = apiPosts.map { apiPost ->
                        Post(
                            postId = apiPost.postId ?: UUID.randomUUID().toString(), // Ensure postId is never null
                            userName = apiPost.userName ?: "Unknown User",
                            subject = apiPost.subject ?: "No Subject",
                            content = apiPost.content ?: "",
                            createdAt  = apiPost.createdAt ?: LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) // Provide a default time
                        )
                    }

                    // Save API posts to local database for offline access
                    if (posts.isNotEmpty()) {
                        withContext(Dispatchers.IO) {
                            // Use insertPosts for potentially better performance with multiple items
                            postDao.insertPosts(posts)
                        }
                    }
                    posts
                } catch (e: Exception) {
                    // Log the specific API error
                    println("API Error fetching posts: ${e.message}")
                    e.printStackTrace()
                    // Fallback to local DB on API error
                    withContext(Dispatchers.IO) {
                        postDao.getAllPosts().first()
                    }
                }
            } else {
                // Fetch from local DB if offline
                println("Network unavailable. Fetching posts from local database.")
                withContext(Dispatchers.IO) {
                    postDao.getAllPosts().first()
                }
            }
        } catch (e: Exception) {
            // Log general errors
            println("Error in getAllPosts: ${e.message}")
            e.printStackTrace()
            emptyList() // Return empty list on failure
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun createPost(): Boolean {
        return try {
            if (isNetworkAvailable()) {
                val apiResponse = withContext(Dispatchers.IO) {
                    apiService.createTweet()
                }

                postDao.insertPost(apiResponse)
                println("Post created via API and saved locally.")
                return true
            } else {
                val randomPost = createLocalPost()
                postDao.insertPost(randomPost)
                println("Network unavailable. Local post created.")
                return true
            }
        } catch (e: Exception) {
            println("Error creating post: ${e.message}")
            e.printStackTrace()
            return false
        }
    }

    // Function to delete all posts from the local database
    suspend fun deleteAllPosts() {
        withContext(Dispatchers.IO) {
            try {
                postDao.deleteAllPosts()
                println("All posts deleted from local database.")
            } catch (e: Exception) {
                println("Error deleting all posts: ${e.message}")
                e.printStackTrace()
            }
        }
    }


    private fun createLocalPost(): Post {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val now = LocalDateTime.now()
        val formatDateTime = now.format(formatter)

        val userNames = listOf(
            "John Smith", "Emma Wilson", "Michael Brown", "Sophia Johnson",
            "Robert Davis", "Olivia Jones", "David Miller", "Ava Garcia"
        )

        val subjects = listOf(
            "Local Thoughts", "Offline Update", "Random Musings", "Device Note"
        )
        val contentTemplates = listOf(
            "Just had an amazing experience with %s!",
            "Does anyone else think %s is overrated?",
            "I can't believe what's happening in %s right now.",
            "Looking for recommendations about %s.",
            "My thoughts on %s: absolutely fascinating!",
            "Today I learned something new about %s.",
            "Thinking about %s while offline.",
            "Quick note about %s."
        )

        // Generate post content
        val selectedSubject = subjects.random()
        val content = contentTemplates.random().format(selectedSubject.lowercase())

        // Create post and return it
        return Post(
            postId = UUID.randomUUID().toString(),
            userName = userNames.random(),
            subject = selectedSubject,
            content = content,
            createdAt = formatDateTime
        )
    }
}