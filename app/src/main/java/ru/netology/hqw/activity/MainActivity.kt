package ru.netology.hqw.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.hqw.R
import ru.netology.hqw.adapter.PostAdapter
import ru.netology.hqw.databinding.ActivityMainBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListeners {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onReply(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val replyIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(replyIntent)
            }

            override fun onYoutube(post: Post) {
                if (post.video != null){
                    val intent = intent.apply {
                        action = Intent.ACTION_VIEW
                        Uri.parse(post.video)
                    }
                    if (intent.resolveActivity(packageManager) != null){
                        startActivity(intent)
                    }
                    else {
                        Log.d("Intent", "Cant resolve")
                    }
                }
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            newPostLauncher.launch(post.content)
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch(null)
        }

    }
}

