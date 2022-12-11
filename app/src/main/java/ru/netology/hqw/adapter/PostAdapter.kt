package ru.netology.hqw.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.hqw.R
import ru.netology.hqw.databinding.PostCardBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.repository.PostDiffCallback

class PostAdapter (private val onInteractionListeners: OnInteractionListeners) : ListAdapter<Post, PostViewHolder>(
    PostDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListeners)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
        holder.bindLike(post)
        holder.bindReplies(post)
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListeners: OnInteractionListeners
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListeners.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListeners.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bindLike(post: Post) {
        binding.apply {
            if (post.likedByMe)
                likes.text = (post.likes + 1).toString()
            else likes.text = (post.likes).toString()
            likes.setOnClickListener {
                onInteractionListeners.onLike(post)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bindReplies(post: Post) {
        binding.apply {
            if (post.repliedByMe)
                replies.text = (post.replies + 1).toString()
            else replies.text = (post.replies).toString()
            replies.setOnClickListener {
                onInteractionListeners.onReply(post)
            }
        }
    }
}