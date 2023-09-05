package ru.netology.hqw.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.netology.hqw.dto.Post
import java.util.concurrent.TimeUnit


private const val BASE_URL = "http://10.0.2.2:9999"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
interface PostApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @POST("posts")
    fun savePost(@Body post: Post): Call<Post>

    @POST("posts/{id}/likes")
    fun likeById (@Path("id") id: Long): Call<Post>

 /*   @POST("posts/{id}/replies")
    fun replyById (@Path("id") id: Long): Call<Post>*/

    @DELETE("posts/{id}/likes")
    fun unlikeById(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Long): Call<Unit>
}

object ApiService{
    val api: PostApiService by lazy {
        retrofit.create()
    }
}