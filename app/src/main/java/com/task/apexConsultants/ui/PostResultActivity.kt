package com.task.apexConsultants.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import apexConsultants.databinding.ActivityResultBinding
import com.task.apexConsultants.MVVM.MainViewModel
import com.task.apexConsultants.util.Constants
import com.task.apexConsultants.util.takeIfError
import com.task.apexConsultants.util.takeIfLoading
import com.task.apexConsultants.util.takeIfSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ID = intent.getIntExtra(Constants.ARG_ID, 0)
        lifecycleScope.launch {
            viewModel.loadPostsById(ID)
        }
        viewModel.singlePostResponse.observe(this) { data ->
            data.takeIfLoading {
                binding.progressBar.visibility = View.VISIBLE
            }
            data.takeIfSuccess {
                binding.progressBar.visibility = View.GONE
                binding.tvTitle.text = this.data.title
                binding.tvbody.text = this.data.body
            }
            data.takeIfError {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}