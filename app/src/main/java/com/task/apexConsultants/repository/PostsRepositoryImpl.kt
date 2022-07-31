package com.task.apexConsultants.repository

import com.task.apexConsultants.api.ApiService
import com.task.apexConsultants.response.Post
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostsRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }

    override suspend fun getPostsById(id: Int?): Post {
        return apiService.getPostsByID(id)
    }
}



