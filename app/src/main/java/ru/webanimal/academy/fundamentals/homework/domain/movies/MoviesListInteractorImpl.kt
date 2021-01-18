package ru.webanimal.academy.fundamentals.homework.domain.movies

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.webanimal.academy.fundamentals.homework.data.network.dto.GenreDto
import ru.webanimal.academy.fundamentals.homework.data.network.dto.ListMovieDto
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesLocalDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesRemoteDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.ListMovie

class MoviesListInteractorImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MoviesListInteractor {

    // FIXME: Remove after cache implementation.
    private var cachedGenres = mutableListOf<GenreDto>()

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<ListMovie> =
        withContext(Dispatchers.IO) {
            val moviesDto = remoteDataSource.getMoviesAsync(LANGUAGE, page = 1)
                .movies

            if (cachedGenres.isEmpty()) {
                cachedGenres = remoteDataSource.getGenresAsync(LANGUAGE)
                    .genres
                    .toMutableList()
            }

            // TODO: add load "isFavorite" from cache.

            val res = moviesDto.mapTo(mutableListOf()) { fromDto(it, cachedGenres) }
            Log.d("TEST::", "result:$res")
            res
        }

    private fun fromDto(movieDto: ListMovieDto, genresDto: List<GenreDto>): ListMovie =
        ListMovie(
            id = movieDto.id,
            title = movieDto.title,
            overview = movieDto.overview,
            genres = toGenresAsString(movieDto, genresDto),
            allowedAge = normalizedAllowedAge(movieDto.isAdult),
            rating = normalizedRating(movieDto.rating),
            reviewsCounter = movieDto.reviewsCounter,
            popularity = normalizedPopularity(movieDto.popularity),
            releaseDate = movieDto.releaseDate,
            posterList = normalizedImgUrl(imagesBaseUrl, movieDto.posterList),
        )

    private fun normalizedAllowedAge(isAdult: Boolean): String = if (isAdult) {
        AGE_ADULT

    } else {
        AGE_CHILD
    }

    private fun normalizedRating(rating: Float): Int {
        return (rating / 2).toInt()
    }

    private fun normalizedPopularity(popularity: Float): Float {
        return (popularity * 100).toInt().toFloat() / 100
    }

    private fun toGenresAsString(movieDto: ListMovieDto, genresDto: List<GenreDto>) =
        genresDto.filter { movieDto.genres.contains(it.id) }
            .map { it.name }
            .joinToString { "" }

    private fun normalizedImgUrl(imagesBaseUrl: String, path: String?) =
        "$imagesBaseUrl${path?.removePrefix("/")}"
}

private const val LANGUAGE = "en-US"
private const val AGE_ADULT = "18"
private const val AGE_CHILD = "13"