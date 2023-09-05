package ru.netology.hqw.repository

import ru.netology.hqw.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun getAllAsync(callback: Callback <List<Post>>){}
    fun likeById(id: Long, callback: Callback<Post>)
    fun unLikeById(id: Long, callback: Callback<Post>)
    //fun replyById(id: Long, callback: Callback<Post>): Post
    fun removeById(id: Long, callback: Callback<Unit>)
    fun save(post: Post, callback: Callback<Post>)

    interface Callback <T>{
        fun onSuccess(value: T){}
        fun onError(e: Exception){}
    }
}