package ru.netology.hqw.viewModel

import androidx.lifecycle.LiveData
import ru.netology.hqw.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun likesCount()
    fun reply()
    fun repliesCount()
}