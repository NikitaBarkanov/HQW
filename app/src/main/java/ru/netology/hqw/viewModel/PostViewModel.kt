package ru.netology.hqw.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.hqw.dto.Post
import ru.netology.hqw.models.FeedModel
import ru.netology.hqw.repository.PostRepository
import ru.netology.hqw.repository.PostRepositoryImpl
import ru.netology.hqw.utils.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = " ",
    authorAvatar = " ",
    content = " ",
    published = " ",
    likedByMe = false,
    likes = 0,
    repliedByMe = false,
    replies = 0,
    video = "https://www.youtube.com/watch?v=jIIW8lnqPYU&list=PLONqCDlUR74rHLRSTKl4UjpiFcn2E8M6O",
    views = 0,
    attachment = null,
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    private val _errorLike = SingleLiveEvent<Throwable>()

    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            // Начинаем загрузку
            _data.postValue(FeedModel(loading = true))
            try {
                // Данные успешно получены
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                // Получена ошибка
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(value: Post) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                val newPosts = _data.value?.posts.orEmpty()
                    .map {
                        if (it.id == id) it
                            .copy(likedByMe = value.likedByMe, likes = value.likes) else it
                    }
                _data.postValue(FeedModel(posts = newPosts))
            }

            override fun onError(e: Exception) {
                _errorLike.value = e
            }
        })
    }

    fun unlikeById(id: Long) {
        repository.unLikeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                val newPosts = _data.value?.posts.orEmpty()
                    .map {
                        if (it.id == id) it
                            .copy(likedByMe = value.likedByMe, likes = value.likes) else it
                    }
                _data.postValue(FeedModel(posts = newPosts))
            }

            override fun onError(e: Exception) {
                _errorLike.value = e
            }
        })
    }
    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(value: Unit) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }

            override fun onError(e: Exception) {
                val old = _data.value?.posts.orEmpty()
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }
}