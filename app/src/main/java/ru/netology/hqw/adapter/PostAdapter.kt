package ru.netology.hqw.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.hqw.R
import ru.netology.hqw.databinding.PostCardBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.repository.PostDiffCallback

class PostAdapter (private val onInteractionListeners: OnInteractionListeners) :
    ListAdapter<Post, PostViewHolder>(
    PostDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListeners)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListeners,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"

    }

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            likes.text = "${post.likes}"



            Glide.with(avatar)
                .load("${BASE_URL}/avatars/${post.authorAvatar}")
                .circleCrop()
                .placeholder(R.drawable.ic_loading_24)
                .error(R.drawable.ic_error_24)
                .timeout(10_000)
                .into(avatar)

            .let {
                if (post.attachment != null) {
                    Glide.with(attachment)
                        .load("${BASE_URL}/images/${post.attachment.url}")
                        .override(attachment.width, attachment.height)
                        .placeholder(R.drawable.ic_loading_24)
                        .error(R.drawable.ic_error_24)
                        .timeout(10_000)
                        .into(attachment)
                }
            }
            attachment.isVisible = post.attachment != null
            videoGroup.isVisible = post.video != null


            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            likes.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            replies.setOnClickListener {
                onInteractionListener.onReply(post)
            }
        }
    }
}
