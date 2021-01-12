package ru.webanimal.academy.fundamentals.homework.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.webanimal.academy.fundamentals.homework.data.network.apis.MoviesApi

class MoviesNetworkModule(baseUrl: String) : MoviesNetworkClient {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val moviesClient = OkHttpClient.Builder()
        .addInterceptor(MoviesApiQueryInterceptor(hashMapOf("api_key" to MOVIES_API_KEY)))
        .build()

    private val moviesRetrofit = Retrofit.Builder()
        .client(moviesClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val moviesApi = moviesRetrofit.create(MoviesApi::class.java)

    override fun moviesApi(): MoviesApi = moviesApi
}

private const val MOVIES_API_KEY = "4a2261d9939098fa2cd9d41651bdc5df"