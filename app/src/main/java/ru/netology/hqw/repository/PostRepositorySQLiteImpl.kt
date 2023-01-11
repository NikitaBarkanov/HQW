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
        dao.likeById(Id)
        posts = posts.map {
            if (it.id != Id) it
            else it.copy(likedByMe = !it.likedByMe, likes = likesCount(it))
        }
        data.value = posts
    }

    private fun likesCount(post: Post): Int{
       val ps: Post = if (post.likedByMe) {post.copy(likes = post.likes - 1)}
        else {post.copy(likes = post.likes + 1)}
        return ps.likes
    }

    override fun replyById(Id: Long) {
        dao.replyById(Id)
        posts = posts.map {
            if (it.id != Id) it else it.copy(repliedByMe = !it.repliedByMe, replies = repliesCount(it))
        }
        data.value = posts
    }
    private fun repliesCount(post: Post): Int{
        val ps: Post = if (post.repliedByMe) {post.copy(replies = post.replies - 1)}
        else {post}
        return ps.replies
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        dao.save(post)
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