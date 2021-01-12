package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import ru.webanimal.academy.fundamentals.homework.data.network.apis.MoviesApi
import ru.webanimal.academy.fundamentals.homework.data.network.dto.MovieDto

class MoviesRemoteDataSourceImpl(private val moviesApi: MoviesApi) : MoviesRemoteDataSource {

    override suspend fun getMoviesAsync(): List<MovieDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieByIdAsync(movieId: Int): MovieDto? {
        TODO("Not yet implemented")
    }
}