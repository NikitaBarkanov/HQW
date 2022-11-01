package ru.netology.hqw

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.hqw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(binding.likes)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22`September at 0:12",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.\n\nНо самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → https://is.gd/xqtzIE",
            likedByMe = false,
            repliedByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_baseline_liked_24)
            }
            likesCount.text = post.likes.toString()

            root.setOnClickListener {
                Log.d("resRoot", "rootSetOncl")
            }

            avatar.setOnClickListener {
                Log.d("avatar", "avatar")
            }
            likes.setOnClickListener {
                Log.d("staff", "staff")
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_liked_24 else R.drawable.ic_baseline_favorite_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount.text = post.likes.toString()
            }
            replies.setOnClickListener {
                Log.d("replies", "replies")
                post.repliedByMe = !post.repliedByMe
                if (post.repliedByMe) post.replies++ else post.replies--
                repliesCount.text = post.replies.toString()
            }
            views.setOnClickListener {
                Log.d("ttt", "ttt")
                post.views++
                viewsCount.text = post.views.toString()
            }

            binding.root.setOnClickListener {
                Log.d("root", "root")
                println("root")
            }

            binding.likes.setOnClickListener {
                Log.d("ssss", "ssss")
                println("likes")
            }

        }
    }
}