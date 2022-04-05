package com.example.doomscrollingforgodot.network

import kotlinx.serialization.Serializable

@Serializable
data class NewsSearchResult(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsItem>
)

@Serializable
data class NewsItem(
    val source: NewsSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
) {
   companion object {
       fun empty() =  NewsItem(
           NewsSource.empty(), "", "", "", "", "", "", ""
       )
   }
}

@Serializable
data class NewsSource(
    val id: String?,
    val name: String?,
) {
   companion object {
       fun empty() = NewsSource("","")
   }
}