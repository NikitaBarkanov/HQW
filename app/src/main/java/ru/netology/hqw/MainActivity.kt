package ru.netology.hqw

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import ru.netology.hqw.R.layout.activity_main

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        textView = findViewById(R.id.likes)
        textView = findViewById(R.id.comments)
        textView = findViewById(R.id.replies)
        textView = findViewById(R.id.views)
    }

/*    fun onClick(view: View) {
        val fCount = textView.text.toString()
        var count: Int = Integer.parseInt(fCount)
        count++
        textView.text = count.toString()
        print(view)
    }*/
}