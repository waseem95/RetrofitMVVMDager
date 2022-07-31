package com.task.apexConsultants.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.apexConsultants.response.Post
import com.task.apexConsultants.repository.PostsRepository
import com.task.apexConsultants.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class MainViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val _postsResponse = MutableLiveData<UIState<List<Post>>>()
    val postsResponse: LiveData<UIState<List<Post>>>
        get() = _postsResponse

    private val _singlePostResponse = MutableLiveData<UIState<Post>>()
    val singlePostResponse: LiveData<UIState<Post>>
        get() = _singlePostResponse


    // getting posts list using
    // repository and passing it into live data
    suspend fun getPosts() {
        viewModelScope.launch {
            if (_postsResponse.value == UIState.Loading) {
                return@launch
            }
            _postsResponse.value = UIState.Loading
            val data = postsRepository.getPosts()
            if (data.isNotEmpty()) {
                _postsResponse.value = UIState.Success(data = data)
            } else {
                _postsResponse.value = UIState.Failure("Some error occurred")

            }
        }
    }

    suspend fun loadPostsById(id : Int?) {
        viewModelScope.launch {
            if (_singlePostResponse.value == UIState.Loading) {
                return@launch
            }
            _singlePostResponse.value = UIState.Loading
            val data = postsRepository.getPostsById(id)
            _singlePostResponse.value = UIState.Success(data = data)
        }
    }
}

