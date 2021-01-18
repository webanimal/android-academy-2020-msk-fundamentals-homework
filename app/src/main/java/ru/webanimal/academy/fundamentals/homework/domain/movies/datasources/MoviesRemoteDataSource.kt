package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import ru.webanimal.academy.fundamentals.homework.data.network.dto.*

interface MoviesRemoteDataSource {
    suspend fun getMoviesAsync(language: String, page: Int): MoviesResultDto
    suspend fun getGenresAsync(language: String): GenresResultDto
    suspend fun getCreditsByMovieId(movieId: Int, language: String): CreditsResultDto
    suspend fun getMovieByIdAsync(movieId: Int, language: String): DetailsMovieDto
}