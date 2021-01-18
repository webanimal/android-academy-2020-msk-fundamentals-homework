package ru.webanimal.academy.fundamentals.homework.domain.movies

import ru.webanimal.academy.fundamentals.homework.domain.movies.models.ListMovie

interface MoviesListInteractor {
    suspend fun getMoviesAsync(forceRefresh: Boolean = false): List<ListMovie>
//    suspend fun updateMovieAsync(movie: ListMovie)
}