package ru.netology.hqw.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.hqw.R
import ru.netology.hqw.databinding.PostCardBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.repository.PostDiffCallback


typealias OnLikeListener = (post : Post) -> Unit
typealias OnReplyListener = (post: Post) -> Unit

class PostAdapter (private val onLikeListener: OnLikeListener,
                   private val onReplyListener: OnReplyListener) : ListAdapter<Post, PostViewHolder>(
    PostDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onReplyListener)
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
    private val onLikeListener: OnLikeListener,
    private val onReplyListener: OnReplyListener
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_favorite_24
            )
/*            likes.setOnClickListener {
                onLikeListener(post)
            }*/
        }
    }

    @SuppressLint("SetTextI18n")
    fun bindLike(post: Post) {
        binding.apply {
            if (post.likedByMe)
                likesCount.text = (post.likes + 1).toString()
            else likesCount.text = (post.likes).toString()
            likes.setOnClickListener {
                onLikeListener(post)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bindReplies(post: Post) {
        binding.apply {
            if (post.repliedByMe)
                repliesCount.text = (post.replies + 1).toString()
            else repliesCount.text = (post.replies).toString()
            replies.setOnClickListener {
                onReplyListener(post)
            }
        }


    }
}

