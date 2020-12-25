package ru.webanimal.academy.fundamentals.homework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSource
import ru.webanimal.academy.fundamentals.homework.presentation.moviedetails.MovieDetailsViewModel
import ru.webanimal.academy.fundamentals.homework.presentation.movies.MoviesListViewModel

class ViewModelFactory(private val dataSource: MoviesDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MoviesListViewModel::class.java -> MoviesListViewModel(dataSource)
            MovieDetailsViewModel::class.java -> MovieDetailsViewModel(dataSource)
            else -> throw IllegalArgumentException("ViewModel of class:$modelClass is not supported")
        } as T
    }
}