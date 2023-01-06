package ru.netology.hqw.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.hqw.dto.Post
import ru.netology.hqw.helper.AppDb
import ru.netology.hqw.repository.PostRepository
import ru.netology.hqw.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    author = " ",
    content = " ",
    published = " ",
    likedByMe = false,
    likes = 0,
    repliedByMe = false,
    replies = 0,
    video = "XXXDFDS",
    views = 0
)

class PostViewModel(application: Application): AndroidViewModel(application){

    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let{
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value?.copy(content = text)
    }

    fun likeById (id : Long) = repository.likeById(id)
    fun removeById (id: Long) = repository.removeById(id)
}