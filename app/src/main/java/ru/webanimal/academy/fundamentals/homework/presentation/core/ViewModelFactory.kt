package ru.webanimal.academy.fundamentals.homework.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesInteractor
import ru.webanimal.academy.fundamentals.homework.presentation.moviedetails.MovieDetailsViewModel
import ru.webanimal.academy.fundamentals.homework.presentation.movies.MoviesListViewModel

class ViewModelFactory(private val moviesInteractor: MoviesInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MoviesListViewModel::class.java -> MoviesListViewModel(moviesInteractor)
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel(moviesInteractor)
            else -> throw IllegalArgumentException("ViewModel of class:$modelClass is not supported")
        } as T
    }
}