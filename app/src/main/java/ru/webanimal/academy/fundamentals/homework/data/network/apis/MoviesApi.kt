package ru.webanimal.academy.fundamentals.homework.data.network.apis

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.webanimal.academy.fundamentals.homework.data.network.dto.*

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesResultDto

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("language") language: String): GenresResultDto

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsByMovieId(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): CreditsResultDto

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): DetailsMovieDto
}