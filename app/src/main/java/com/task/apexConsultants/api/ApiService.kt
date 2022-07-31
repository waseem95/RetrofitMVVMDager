package com.task.apexConsultants.api

import com.task.apexConsultants.response.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("/posts/{id}")
    suspend fun getPostsByID(@Path("id") id: Int?): Post

}