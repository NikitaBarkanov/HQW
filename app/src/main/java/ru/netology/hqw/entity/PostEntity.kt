package ru.netology.hqw.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.hqw.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val repliedByMe: Boolean = false,
    val replies: Int = 0,
    val views: Int = 0,
    val video: String?
) {
    fun toDto() = Post(id, author, content, published, likedByMe, likes, repliedByMe, replies, views, video)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.likedByMe,
                dto.likes,
                dto.repliedByMe,
                dto.replies,
                dto.views,
                dto.video)

    }
}