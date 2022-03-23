package com.example.doomscrollingforgodot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.doomscrollingforgodot.data.SpokenLinesRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lines = SpokenLinesRepository.getInstance(this).lines

        findViewById<TextView>(R.id.welcome_text).also {
            it.text = lines[0].line
        }

    }
}