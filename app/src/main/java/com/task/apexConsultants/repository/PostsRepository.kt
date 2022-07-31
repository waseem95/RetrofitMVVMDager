package com.task.apexConsultants.repository

import com.task.apexConsultants.response.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getPostsById(id:Int?): Post
}