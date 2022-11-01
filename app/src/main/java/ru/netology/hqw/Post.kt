package ru.netology.hqw

data class Post (
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var repliedByMe: Boolean = false,
    var replies: Int = 0,
    var views: Int = 0
)