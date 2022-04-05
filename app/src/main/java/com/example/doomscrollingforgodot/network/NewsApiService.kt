package com.example.doomscrollingforgodot.network

import com.example.doomscrollingforgodot.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = BuildConfig.NEWS_API_KEY
const val BASE_URL = "https://newsapi.org/v2/"

/**
 * Client for free API service for getting news headlines.
 * Docs: https://newsapi.org/docs/endpoints/top-headlines
 **/
interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int = 1,
    ): NewsSearchResult
}

@ExperimentalSerializationApi
object NewsApi {
    val SERVICE: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(NewsApiService::class.java)
    }
}
