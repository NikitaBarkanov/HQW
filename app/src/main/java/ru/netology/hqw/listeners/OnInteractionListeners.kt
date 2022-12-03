package ru.netology.hqw.listeners

import ru.netology.hqw.dto.Post

interface OnInteractionListeners {
    fun onLike(post: Post){}
    fun onRemove(post: Post){}
    fun onEdit(post: Post){}
    fun onReply (post: Post) {}
}
