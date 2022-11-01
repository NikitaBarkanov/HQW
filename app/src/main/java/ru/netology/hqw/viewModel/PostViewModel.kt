package ru.netology.hqw.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.hqw.dto.Post
import ru.netology.hqw.repository.PostRepositoryInMemoryImpl


class PostViewModel: ViewModel(){
    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState


    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    init{
        val post = repository.get().value
        changeState(ScreenState.Working(post ?: Post()))
    }

    private fun changeState(newState: ScreenState){
        _screenState.value = newState
    }

    fun like(){
        try{
            repository.like()
            repository.likesCount()
            //repository.likesCount()
            changeState(ScreenState.Working(repository.get().value ?: Post()))
        } catch (e: Throwable) {
            Log.e("PostViewModel", "like: $e", )
            changeState(ScreenState.Error)
        }
    }

    fun reply(){
        repository.reply()
        repository.repliesCount()
        //repository.repliesCount()
        changeState(ScreenState.Working(repository.get().value ?: Post()))
    }

}