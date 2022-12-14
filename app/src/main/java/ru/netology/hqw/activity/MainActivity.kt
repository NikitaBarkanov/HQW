package ru.netology.hqw.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.hqw.R
import ru.netology.hqw.adapter.PostAdapter
import ru.netology.hqw.databinding.ActivityMainBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.utils.AndroidUtils.focusAndShowKeyboard
import ru.netology.hqw.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editingGroup.visibility = View.GONE

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

            override fun onReply(post: Post){
                viewModel.replyById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                binding.editingGroup.visibility = View.GONE
                requestFocus()
                setText(post.content)
            }
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                focusAndShowKeyboard()
            }
        }

        binding.cancel.setOnClickListener {
            with(binding.content) {
                viewModel.cancel()
                clearFocus()
                focusAndShowKeyboard()
                binding.editingGroup.visibility = View.VISIBLE
            }
        }
    }
}
