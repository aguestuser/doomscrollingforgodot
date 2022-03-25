package com.example.doomscrollingforgodot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.doomscroller.SpokenLinesAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spokenLinesRepository = SpokenLinesRepository.getInstance(this)
        val recyclerView = findViewById<RecyclerView>(R.id.doomscroller_recycler).also {
            it.adapter = SpokenLinesAdapter(spokenLinesRepository.lines)
        }
    }
}