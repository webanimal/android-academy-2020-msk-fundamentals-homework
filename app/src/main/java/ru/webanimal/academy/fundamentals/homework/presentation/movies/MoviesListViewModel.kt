package ru.webanimal.academy.fundamentals.homework.presentation.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesListInteractor
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.ListMovie

class MoviesListViewModel(private val moviesListInteractor: MoviesListInteractor) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "ExceptionHandler throwable:$throwable")
    }

    private val innerMovies = MutableLiveData<List<ListMovie>>(emptyList())

    val movies: LiveData<List<ListMovie>> get() = innerMovies

    fun onViewCreated() {
        viewModelScope.launch(coroutineExceptionHandler) {
            innerMovies.value = moviesListInteractor.getMoviesAsync(false)
        }
    }

    fun onFavoriteClick(movie: ListMovie) {
        viewModelScope.launch(coroutineExceptionHandler) {
//            moviesListInteractor.updateMovieAsync(movie)
//            innerMovies.value = moviesListInteractor.getMoviesAsync()
        }
    }

    companion object {
        private val TAG = MoviesListViewModel::class.java.simpleName
    }
}