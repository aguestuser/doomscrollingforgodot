package com.example.doomscrollingforgodot.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.doomscrollingforgodot.R
import java.io.InputStream
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SpokenLinesDataSource private constructor(private val inputStream: InputStream) {
    companion object Factories {
        fun getInstance(context: Context) = SpokenLinesDataSource(
            context.resources.openRawResource(R.raw.fulltext)
        )

        @VisibleForTesting
        fun getTestInstance() = SpokenLinesDataSource(
            {}::class.java.classLoader!!.getResourceAsStream("fulltext.json")
        )
    }

    fun consume(): List<SpokenLine> =
        inputStream.bufferedReader().use {
            Json.decodeFromString(it.readText())
        }
}

