package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import ru.webanimal.academy.fundamentals.homework.data.network.apis.MoviesApi
import ru.webanimal.academy.fundamentals.homework.data.network.dto.*

class MoviesRemoteDataSourceImpl(private val moviesApi: MoviesApi) : MoviesRemoteDataSource {

    override suspend fun getMoviesAsync(language: String, page: Int): MoviesResultDto =
        moviesApi.getPopularMovies(language, page)

    override suspend fun getGenresAsync(language: String): GenresResultDto =
        moviesApi.getGenres(language)

    override suspend fun getCreditsByMovieId(movieId: Int, language: String): CreditsResultDto =
        moviesApi.getCreditsByMovieId(movieId, language)

    override suspend fun getMovieByIdAsync(movieId: Int, language: String): DetailsMovieDto =
        moviesApi.getMovieById(movieId, language)
}