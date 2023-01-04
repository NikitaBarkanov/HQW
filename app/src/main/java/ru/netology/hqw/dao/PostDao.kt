package ru.netology.hqw.dao

import ru.netology.hqw.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(Id: Long)
    fun replyById(Id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
}