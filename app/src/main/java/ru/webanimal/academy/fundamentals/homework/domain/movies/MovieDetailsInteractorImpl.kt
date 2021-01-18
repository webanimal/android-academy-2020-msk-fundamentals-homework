package ru.webanimal.academy.fundamentals.homework.domain.movies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.webanimal.academy.fundamentals.homework.data.network.dto.CastDto
import ru.webanimal.academy.fundamentals.homework.data.network.dto.DetailsMovieDto
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesLocalDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesRemoteDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.Actor
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.DetailsMovie

class MovieDetailsInteractorImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val imagesBaseUrl: String
) : MovieDetailsInteractor {

    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): DetailsMovie =
        withContext(Dispatchers.IO) {
            val movieDto = remoteDataSource.getMovieByIdAsync(movieId, LANGUAGE)

            val actorsDto = remoteDataSource.getCreditsByMovieId(movieId, LANGUAGE)
                .casts

            // TODO: add load "isFavorite" from cache.

            detailsMovieFromDto(movieDto, actorsDto)
        }

    private fun detailsMovieFromDto(
        movieDto: DetailsMovieDto,
        actorsDto: List<CastDto>
    ): DetailsMovie {

        return DetailsMovie(
            id = movieDto.id,
            title = movieDto.title,
            overview = movieDto.overview,
            allowedAge = normalizedAllowedAge(movieDto.isAdult),
            rating = normalizedRating(movieDto.rating),
            reviewsCounter = movieDto.reviewsCounter,
            popularity = normalizedPopularity(movieDto.popularity),
            releaseDate = movieDto.releaseDate,
            duration = movieDto.duration,
            budget = movieDto.budget,
            revenue = movieDto.revenue,
            status = movieDto.status,
            genres = getGenresAsString(movieDto),
            homepage = movieDto.homepage,
            posterDetails = normalizedImgUrl(imagesBaseUrl, movieDto.posterDetails),
            actors = toActorsList(actorsDto, movieDto.id)
        )
    }

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

    private fun getGenresAsString(movieDto: DetailsMovieDto) =
        movieDto.genres.joinToString { it.name }

    private fun normalizedImgUrl(imagesBaseUrl: String, path: String?) =
        "$imagesBaseUrl${path?.removePrefix("/")}"

    private fun toActorsList(actorsDto: List<CastDto>, movieId: Int): List<Actor> =
        actorsDto.sortedBy { it.orderInCredits }
            .takeWhile { it.orderInCredits < ACTORS_LIMIT }
            .mapTo(mutableListOf()) {
                Actor(
                    movieId = movieId,
                    castId = it.castId,
                    orderInCredits = it.orderInCredits,
                    name = it.name,
                    character = it.character,
                    imageUrl = normalizedImgUrl(imagesBaseUrl, it.profileImgUrl)
                )
            }
}

private const val LANGUAGE = "en-US"
private const val AGE_ADULT = "18"
private const val AGE_CHILD = "13"
private const val ACTORS_LIMIT = 10