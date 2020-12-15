package ru.webanimal.academy.fundamentals.homework.domain.movies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.webanimal.academy.fundamentals.homework.data.JsonLoader
import ru.webanimal.academy.fundamentals.homework.data.models.Movie

interface MoviesDataSource {
    suspend fun getMoviesAsync(forceRefresh: Boolean = false): List<Movie>
    suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean = false): Movie?
    suspend fun updateMovieAsync(movie: Movie)
}

class MoviesDataSourceImpl(private val jsonLoader: JsonLoader) : MoviesDataSource {
    
    private var cachedMovies: MutableList<Movie> = mutableListOf()

    override suspend fun getMoviesAsync(forceRefresh: Boolean): List<Movie> = withContext(Dispatchers.IO) {
        if (cachedMovies.isEmpty() || forceRefresh) {
            cachedMovies = jsonLoader.loadMoviesAsync().toMutableList()
        }
        ArrayList(cachedMovies)
    }

    override suspend fun getMovieByIdAsync(movieId: Int, forceRefresh: Boolean): Movie? = withContext(Dispatchers.IO) {
        if (cachedMovies.isEmpty() || forceRefresh) {
            cachedMovies = jsonLoader.loadMoviesAsync().toMutableList()
        }
        cachedMovies.find { movieId == it.id }
    }
    
    override suspend fun updateMovieAsync(movie: Movie) = withContext(Dispatchers.IO) {
        cachedMovies.forEachIndexed { i: Int, it ->
            if (it.id == movie.id) {
                cachedMovies[i] = movie.copy()
                return@forEachIndexed
            }
        }
    }
}