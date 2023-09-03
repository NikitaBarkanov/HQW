package ru.netology.hqw.dto

data class Post (
    val id: Long = 0,
    val avatar: String,
    val author: String = " ",
    val content: String = " ",
    val published: String = " ",
    val likedByMe: Boolean,
    val likes: Int = 0,
    val repliedByMe: Boolean,
    val replies: Int = 0,
    val views: Int = 0,
    val video: String?,
    val attachment: Attachment?
)

data class Attachment(
    val url: String,
    val description: String,
    val type: AttachmentType = AttachmentType.IMAGE
)
enum class AttachmentType {
    IMAGE
}