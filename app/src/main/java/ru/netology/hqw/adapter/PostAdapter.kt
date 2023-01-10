package ru.netology.hqw.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
    private val onInteractionListeners: OnInteractionListeners
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var post: Post

    init {
        binding.likes.setOnClickListener{
            onInteractionListeners.onLike(post)}
        binding.replies.setOnClickListener{
            onInteractionListeners.onReply(post) }
        binding.play.setOnClickListener {
            onInteractionListeners.onYoutube(post) }
        binding.videoCard.setOnClickListener {
            onInteractionListeners.onYoutube(post) }
        binding.videoLink.setOnClickListener {
            onInteractionListeners.onYoutube(post) }
        binding.root.setOnClickListener{
            onInteractionListeners.postLink(post)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        this.post = post
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            videoLink.text = post.video
            likes.text = "${post.likes}"
            replies.isChecked = post.repliedByMe
            replies.text = "${post.replies}"
            if (post.video == null) binding.videoGroup.visibility = View.GONE
            else binding.videoGroup.visibility = View.VISIBLE
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
            likes.setOnClickListener {
                onInteractionListeners.onLike(post)
                }
            replies.setOnClickListener {
                onInteractionListeners.onReply(post)
            }
            binding.root.setOnClickListener {
                onInteractionListeners.postLink(post)
            }
        }

    }
}