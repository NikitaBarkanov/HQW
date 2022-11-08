package ru.netology.hqw.viewModel

import ru.netology.hqw.dto.Post

sealed class ScreenState {
    class Working(var post: Post): ScreenState()
    object Loading: ScreenState()
    object Error: ScreenState()
}