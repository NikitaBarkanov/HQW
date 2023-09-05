package ru.netology.hqw.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.hqw.api.ApiService
import ru.netology.hqw.dto.Post
import java.lang.Exception

class PostRepositoryImpl : PostRepository {

    override fun getAll(): List<Post> {

        return ApiService.api.getPosts()
            .execute()
            .let {
                if(!it.isSuccessful) {
                    error("Response code is ${it.code()}")
                }
                it.body() ?: throw RuntimeException("body is null")
            }
    }

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        ApiService.api.getPosts()
            .enqueue(object : Callback<List<Post>>{
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (!response.isSuccessful){
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                    }

                    val posts = response.body()

                    if (posts == null) {
                        callback.onError(RuntimeException("Body is empty"))
                        return
                    }
                    callback.onSuccess(posts)
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(Exception(t))
                }

            })
    }

    override fun likeById (id: Long, callback: PostRepository.Callback<Post>) {
        ApiService.api.likeById(id)
            .enqueue(object: Callback <Post>{
                override fun onResponse(call: Call <Post>, response: Response <Post>) {
                    try {
                        if (!response.isSuccessful){
                            callback.onError(RuntimeException(response.message()))
                            return
                        }
                        callback.onSuccess( response.body() ?: throw RuntimeException("body is null"))
                    } catch (e: Exception) {
                        callback.onError (e)
                    }
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

    override fun unLikeById(id: Long, callback: PostRepository.Callback<Post>) {
        ApiService.api.unlikeById(id)
            .enqueue(object: Callback <Post>{
                override fun onResponse(call: Call <Post>, response: Response <Post>) {
                    try {
                        if (!response.isSuccessful){
                            callback.onError(RuntimeException(response.message()))
                            return
                        }
                        callback.onSuccess( response.body() ?: throw RuntimeException("body is null"))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }


/*    override fun replyById(id: Long, callback: PostRepository.Callback<Post>): Post {
        return ApiService.api.replyById(id)
            .execute()
            .let { it.body() ?: throw RuntimeException("body is null") }
    }*/

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        ApiService.api.savePost(post)
            .enqueue(object: Callback <Post>{
                override fun onResponse(call: Call <Post>, response: Response <Post>) {
                    try {
                        if (!response.isSuccessful){
                            callback.onError(RuntimeException(response.message()))
                            return
                        }
                        callback.onSuccess( response.body() ?: throw RuntimeException("body is null"))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        ApiService.api.deletePost(id)
            .enqueue(object: Callback <Unit>{

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    try {
                        if (!response.isSuccessful){
                            callback.onError(RuntimeException(response.message()))
                            return
                        }
                        callback.onSuccess(Unit)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback.onError(Exception(t))
                }
            })
    }
}