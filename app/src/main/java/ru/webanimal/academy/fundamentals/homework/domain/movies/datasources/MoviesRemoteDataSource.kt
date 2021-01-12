package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import ru.webanimal.academy.fundamentals.homework.data.network.dto.MovieDto

interface MoviesRemoteDataSource {
    suspend fun getMoviesAsync(): List<MovieDto>
    suspend fun getMovieByIdAsync(movieId: Int): MovieDto?
}