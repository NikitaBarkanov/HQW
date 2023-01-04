package ru.netology.hqw.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.hqw.dao.PostDao
import ru.netology.hqw.dto.Post

class PostRepositorySQLiteImpl(private val dao: PostDao): PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(Id: Long) {
        posts = posts.map {
            if (it.id != Id) it else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts
    }

    override fun replyById(Id: Long) {
        posts = posts.map {
            if (it.id != Id) it else it.copy(repliedByMe = !it.repliedByMe)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
}