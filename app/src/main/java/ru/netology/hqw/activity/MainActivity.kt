package ru.netology.hqw.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.hqw.R
import ru.netology.hqw.databinding.ActivityMainBinding
import ru.netology.hqw.viewModel.PostViewModel
import ru.netology.hqw.viewModel.ScreenState

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels()
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribe()
        setupListeners()
    }

    private fun subscribe() {

        viewModel.screenState.observe(this) { state ->
            when (state) {
                is ScreenState.Working -> showWorking(state)
                is ScreenState.Loading -> {}
                is ScreenState.Error -> {}
            }
        }
    }

    private fun showWorking(state: ScreenState.Working) {
        with(binding) {
            author.text = state.post.author
            published.text = state.post.published
            content.text = state.post.content
            likes.setImageResource(
                    if (state.post.likedByMe) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_favorite_24
                )
            likes.setOnClickListener{
                viewModel.like()
            }
        }
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            Log.d("resRoot", "rootSetOncl")
        }

        binding.avatar.setOnClickListener {
            Log.d("avatar", "avatar")
        }

        binding.replies.setOnClickListener {
            Log.d("replies", "replies")
            //viewModel.reply()
        }

/*        binding.likes.setOnClickListener {
            Log.d("like", "like")
            viewModel.like()
        }*/
    }

}