package ru.netology.hqw.viewModel

import androidx.lifecycle.LiveData
import ru.netology.hqw.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(Id: Long)
    fun replyById(Id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}