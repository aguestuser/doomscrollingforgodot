package com.example.doomscrollingforgodot.data

import android.content.Context
import androidx.annotation.VisibleForTesting

class SpokenLinesRepository private constructor(private val dataSource: SpokenLinesDataSource) {
    companion object Factories {
        fun getInstance(context: Context) = SpokenLinesRepository(
            SpokenLinesDataSource.getInstance(context)
        )

        @VisibleForTesting
        fun getTestInstance() = SpokenLinesRepository(
            SpokenLinesDataSource.getTestInstance()
        )

    }
    val lines: List<SpokenLine> by lazy {
        dataSource.consume()
    }
}