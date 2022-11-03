package ru.netology.hqw.dto

data class Post (
    val id: Int = 0,
    val author: String = " ",
    val content: String = " ",
    val published: String = " ",
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val repliedByMe: Boolean = false,
    val replies: Int = 0,
    val views: Int = 0
)