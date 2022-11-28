package ru.netology.hqw.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.hqw.repository.PostRepositoryInMemoryImpl


class PostViewModel: ViewModel(){

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById (id : Long) = repository.likeById(id)
    fun replyById (id: Long) = repository.replyById(id)

}