package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import ru.webanimal.academy.fundamentals.homework.data.storage.entities.MovieEntity

interface MoviesLocalDataSource {
    suspend fun getMoviesAsync(forceRefresh: Boolean = false): List<MovieEntity>
    suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean = false): MovieEntity?
    suspend fun updateMovieAsync(movieEntity: MovieEntity)
}