package ru.netology.hqw.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.hqw.R
import ru.netology.hqw.adapter.PostAdapter
import ru.netology.hqw.databinding.ActivityMainBinding
import ru.netology.hqw.viewModel.PostViewModel
import ru.netology.hqw.viewModel.ScreenState

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter {
            viewModel.likeById(it.id)
        }
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }
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
            likesCount.text = state.post.likes.toString()
            likes.setImageResource(
                    if (state.post.likedByMe) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_favorite_24
                )
            likesCount.text = state.post.likes.toString()
            repliesCount.text = state.post.replies.toString()
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
            viewModel.replyById(id = binding.root.id.toLong())
        }

       binding.likes.setOnClickListener {
            Log.d("like", "like")
            viewModel.likeById(id = binding.root.id.toLong())
        }
    }

}