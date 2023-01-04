package ru.netology.hqw.dto

data class Post (
    val id: Long = 0,
    val author: String = " ",
    val content: String = " ",
    val published: String = " ",
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val repliedByMe: Boolean = false,
    val replies: Int = 0,
    val views: Int = 0,
    val video: String?
)