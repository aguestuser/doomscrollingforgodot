package com.example.doomscrollingforgodot.doomscroller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doomscrollingforgodot.data.SpokenLine
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.network.NewsItem
import com.example.doomscrollingforgodot.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class DoomScrollerViewModel(
    linesRepository: SpokenLinesRepository,
    private val newsApi: NewsApiService,
): ViewModel() {
    companion object {
        private const val MIN_NEWS_ITEMS = 2
        private const val TAG = "DoomScrollerViewModel"
    }

    val lines: List<SpokenLine> = linesRepository.lines
    private val newsItems: MutableList<NewsItem> = mutableListOf()

    init {
        refreshNewsItems()
    }

    fun takeNewsItem(): NewsItem = when(newsItems.isEmpty()) {
        true -> NewsItem.empty()
        false -> newsItems.removeAt(0).also {
            if (newsItems.size < MIN_NEWS_ITEMS) {
                refreshNewsItems()
            }
        }
    }



    private fun refreshNewsItems() {
        viewModelScope.launch {
            val fetchedItems = withContext(Dispatchers.IO) {
                try {
                    newsApi.getTopHeadlines(page = listOf(1,2,3).random()).articles
                } catch(e: Exception) {
                    Log.e(TAG, "Error refreshing news items: $e")
                    emptyList()
                }

            }
            newsItems.addAll(fetchedItems)
        }
    }
}

