package ru.netology.hqw.repository

import androidx.lifecycle.LiveData
import ru.netology.hqw.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(Id: Long): Post
    fun unLikeById(Id: Long): Post
    fun replyById(Id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}