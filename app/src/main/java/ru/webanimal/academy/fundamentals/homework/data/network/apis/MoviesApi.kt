package ru.webanimal.academy.fundamentals.homework.data.network.apis

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "ru-RUS",
        @Query("page") page: Int = 1
    )
}