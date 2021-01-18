package ru.webanimal.academy.fundamentals.homework.domain.movies

import ru.webanimal.academy.fundamentals.homework.domain.movies.models.DetailsMovie

interface MovieDetailsInteractor {
    suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean = false): DetailsMovie
//    suspend fun updateMovieAsync(movie: ListMovie)
}