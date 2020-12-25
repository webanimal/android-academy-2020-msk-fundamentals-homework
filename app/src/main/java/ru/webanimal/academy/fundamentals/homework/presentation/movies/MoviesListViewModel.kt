package ru.webanimal.academy.fundamentals.homework.presentation.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.webanimal.academy.fundamentals.homework.data.models.Movie
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSource

class MoviesListViewModel(private val dataSource: MoviesDataSource) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "ExceptionHandler throwable:$throwable")
    }

    private val innerMovies = MutableLiveData<List<Movie>>(emptyList())

    val movies: LiveData<List<Movie>> get() = innerMovies

    fun onViewCreated() {
        viewModelScope.launch(coroutineExceptionHandler) {
            innerMovies.value = dataSource.getMoviesAsync(true)
        }
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch(coroutineExceptionHandler) {
            dataSource.updateMovieAsync(movie)
            innerMovies.value = dataSource.getMoviesAsync()
        }
    }

    companion object {
        private val TAG = MoviesListViewModel::class.java.simpleName
    }
}