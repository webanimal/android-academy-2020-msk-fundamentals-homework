package ru.webanimal.academy.fundamentals.homework.domain.movies.datasources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.webanimal.academy.fundamentals.homework.data.storage.JsonLoader
import ru.webanimal.academy.fundamentals.homework.data.storage.entities.MovieEntity

class MoviesLocalDataSourceImpl(private val jsonLoader: JsonLoader) : MoviesLocalDataSource {

    private var cachedMovieEntities: MutableList<MovieEntity> = mutableListOf()

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<MovieEntity> =
        withContext(Dispatchers.IO) {
            if (cachedMovieEntities.isEmpty() || forceRefresh) {
                cachedMovieEntities = jsonLoader.loadMoviesAsync().toMutableList()
            }
            ArrayList(cachedMovieEntities)
        }

    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): MovieEntity? =
        withContext(Dispatchers.IO) {
            if (cachedMovieEntities.isEmpty() || forceRefresh) {
                cachedMovieEntities = jsonLoader.loadMoviesAsync().toMutableList()
            }
            cachedMovieEntities.find { movieId == it.id }
        }

    override suspend fun updateMovieAsync(movieEntity: MovieEntity) = withContext(Dispatchers.IO) {
        cachedMovieEntities.forEachIndexed { i: Int, it ->
            if (it.id == movieEntity.id) {
                cachedMovieEntities[i] = movieEntity.copy()
                return@forEachIndexed
            }
        }
    }
}