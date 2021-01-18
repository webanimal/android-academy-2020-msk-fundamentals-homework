package ru.webanimal.academy.fundamentals.homework.presentation.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.webanimal.academy.fundamentals.homework.domain.movies.MovieDetailsInteractor
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.DetailsMovie

class MovieDetailsViewModel(private val moviesDetailsInteractor: MovieDetailsInteractor) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "ExceptionHandler throwable:$throwable")
    }

    private val innerMovie = MutableLiveData<DetailsMovie>()

    val movie: LiveData<DetailsMovie> get() = innerMovie

    fun onViewCreated(movieId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            innerMovie.value = moviesDetailsInteractor.getMovieByIdAsync(movieId)
        }
    }

    companion object {
        private val TAG = MovieDetailsViewModel::class.java.simpleName
    }
}