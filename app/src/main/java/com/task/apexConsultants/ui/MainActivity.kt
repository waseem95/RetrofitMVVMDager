package com.task.apexConsultants.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import apexConsultants.databinding.ActivityMainBinding
import com.task.apexConsultants.MVVM.MainViewModel
import com.task.apexConsultants.util.takeIfError
import com.task.apexConsultants.util.takeIfLoading
import com.task.apexConsultants.util.takeIfSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var postsAdapter: PostsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        getData()
        viewModel.postsResponse.observe(this) { state ->
            state takeIfLoading {
                if (!binding.refreshIndicator.isRefreshing)
                    showLoader(true)

            }
            state takeIfSuccess {
                binding.refreshIndicator.isRefreshing = false
                showLoader(false)
                postsAdapter.posts = this.data
            }
            state takeIfError {
                binding.refreshIndicator.isRefreshing = false
                showLoader(false)
                Log.e(TAG, this.errorMessage)
            }
        }

        binding.refreshIndicator.setOnRefreshListener {
            getData()
        }

    }

    private fun showLoader(show: Boolean? = false) {
        if (show == true)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getPosts()
        }
    }


    private fun setupRecyclerView() = binding.rvTodos.apply {
        postsAdapter = PostsAdapter()
        adapter = postsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}