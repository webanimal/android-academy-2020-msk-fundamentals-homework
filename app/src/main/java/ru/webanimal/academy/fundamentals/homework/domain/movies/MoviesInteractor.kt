package ru.webanimal.academy.fundamentals.homework.domain.movies

import ru.webanimal.academy.fundamentals.homework.domain.movies.models.Movie

interface MoviesInteractor {
    suspend fun getMoviesAsync(forceRefresh: Boolean = false): List<Movie>
    suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean = false): Movie?
    suspend fun updateMovieAsync(movie: Movie)
}