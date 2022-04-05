package com.example.doomscrollingforgodot.doomscroller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.network.NewsApiService
import kotlinx.serialization.ExperimentalSerializationApi
import java.lang.IllegalArgumentException

@ExperimentalSerializationApi
class DoomScrollerViewModelFactory(
    private val linesRepository: SpokenLinesRepository,
    private val newsApiService: NewsApiService,
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoomScrollerViewModel::class.java)) {
            return DoomScrollerViewModel(linesRepository, newsApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}