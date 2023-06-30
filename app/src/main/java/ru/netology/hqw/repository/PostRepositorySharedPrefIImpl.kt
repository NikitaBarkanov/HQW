/*
package ru.netology.hqw.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.hqw.dto.Post

class PostRepositorySharedPrefIImpl (context : Context): PostRepository {
    private val gson = Gson()
    private var nextId = 1L
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

    init {
        prefs.getString(key,null)?.let {
            posts = gson.fromJson(it, type)
            data.value = posts
        }
    }

    override fun likeById(Id: Long) {
        posts = posts.map {
            if (it.id != Id) it else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts
        sync()
    }



    override fun replyById(Id: Long) {
        posts = posts.map {
            if (it.id != Id) it else it.copy(repliedByMe = !it.repliedByMe)
        }
            data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    private fun sync(){
        with(prefs.edit()){
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}*/
