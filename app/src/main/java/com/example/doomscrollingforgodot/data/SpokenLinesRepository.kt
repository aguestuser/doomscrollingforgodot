package com.example.doomscrollingforgodot.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.doomscrollingforgodot.data.SpokenLinesDataSource.Factories.DEFAULT_TEST_JSON

class SpokenLinesRepository private constructor(private val dataSource: SpokenLinesDataSource) {
    companion object Factories {
        fun getInstance(context: Context) = SpokenLinesRepository(
            SpokenLinesDataSource.getInstance(context)
        )

        @VisibleForTesting
        fun getTestInstance(file: String = DEFAULT_TEST_JSON) = SpokenLinesRepository(
            SpokenLinesDataSource.getTestInstance(file)
        )

    }
    val lines: List<SpokenLine> by lazy {
        dataSource.consume()
    }
}