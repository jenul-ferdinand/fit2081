package com.fit2081.posttweet.data

import retrofit2.http.GET
import retrofit2.http.POST

interface TweetApiService {
    // Gets the posts from the server
    @GET("api/tweets")
    suspend fun getTweets(): List<Post>

    // Posts a post
    @POST("api/tweets/new")
    suspend fun createTweet(): Post
}