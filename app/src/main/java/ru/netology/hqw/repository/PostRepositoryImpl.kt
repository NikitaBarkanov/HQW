package ru.netology.hqw.repository

import androidx.lifecycle.Transformations
import ru.netology.hqw.dao.PostDao
import ru.netology.hqw.dto.Post
import ru.netology.hqw.entity.PostEntity

class PostRepositoryImpl (
    private val dao: PostDao,
) : PostRepository {
    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }

    override fun likeById(Id: Long) {
        dao.likeById(Id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun replyById(Id: Long) {
        dao.replyById(Id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}