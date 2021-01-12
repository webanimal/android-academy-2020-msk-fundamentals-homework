package ru.webanimal.academy.fundamentals.homework.domain.movies

import ru.webanimal.academy.fundamentals.homework.data.storage.entities.ActorEntity
import ru.webanimal.academy.fundamentals.homework.data.storage.entities.MovieEntity
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesLocalDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesRemoteDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.Actor
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.Movie

class MoviesInteractorImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
    ) : MoviesInteractor {

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<Movie> {
        return localDataSource.getMoviesAsync(forceRefresh)
            .mapTo(mutableListOf()) {fromEntity(it) }
    }

    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): Movie? {
        return localDataSource.getMovieByIdAsync(movieId, forceRefresh)?.let { fromEntity(it) }
    }

    override suspend fun updateMovieAsync(movie: Movie) {
        localDataSource.updateMovieAsync(toEntity(movie))
    }

    private fun fromEntity(movieEntity: MovieEntity): Movie {
        val actors = mutableListOf<Actor>()
        movieEntity.actorEntities
            .mapTo(actors) {
                Actor(
                    movieId = it.movieId,
                    name = it.name,
                    image = it.image
                )
            }

        return Movie(
            id = movieEntity.id,
            title = movieEntity.title,
            genres = movieEntity.genres,
            overview = movieEntity.overview,
            allowedAge = movieEntity.allowedAge,
            posterList = movieEntity.posterList,
            posterDetails = movieEntity.posterDetails,
            rating = movieEntity.rating,
            reviewsCounter = movieEntity.reviewsCounter,
            duration = movieEntity.duration,
            isFavorite = movieEntity.isFavorite,
            actors = actors
        )
    }

    private fun toEntity(movie: Movie): MovieEntity {
        val actors = mutableListOf<ActorEntity>()
        movie.actors
            .mapTo(actors) {
                ActorEntity(
                    movieId = it.movieId,
                    name = it.name,
                    image = it.image
                )
            }

        return MovieEntity(
            id = movie.id,
            title = movie.title,
            genres = movie.genres,
            overview = movie.overview,
            allowedAge = movie.allowedAge,
            posterList = movie.posterList,
            posterDetails = movie.posterDetails,
            rating = movie.rating,
            reviewsCounter = movie.reviewsCounter,
            duration = movie.duration,
            isFavorite = movie.isFavorite,
            actorEntities = actors
        )
    }
}