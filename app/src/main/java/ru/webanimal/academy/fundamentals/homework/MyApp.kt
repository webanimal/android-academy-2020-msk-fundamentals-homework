package ru.webanimal.academy.fundamentals.homework

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.webanimal.academy.fundamentals.homework.data.network.MoviesNetworkModule
import ru.webanimal.academy.fundamentals.homework.data.storage.JsonLoader
import ru.webanimal.academy.fundamentals.homework.domain.movies.MovieDetailsInteractor
import ru.webanimal.academy.fundamentals.homework.domain.movies.MovieDetailsInteractorImpl
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesListInteractor
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesListInteractorImpl
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesLocalDataSourceImpl
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesRemoteDataSourceImpl
import ru.webanimal.academy.fundamentals.homework.presentation.core.ViewModelFactory

class MyApp : Application(), AppComponent {

    private lateinit var moviesListInteractor: MoviesListInteractor
    private lateinit var movieDetailsInteractor: MovieDetailsInteractor
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        initInteractors()
        viewModelFactory = ViewModelFactory(
            moviesListInteractor,
            movieDetailsInteractor
        )
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory

    private fun initInteractors() {
        moviesListInteractor = MoviesListInteractorImpl(
            MoviesLocalDataSourceImpl(JsonLoader(this)),
            MoviesRemoteDataSourceImpl(MoviesNetworkModule(BuildConfig.BASE_URL).moviesApi()),
            BuildConfig.BASE_IMG_URL
        )
        movieDetailsInteractor = MovieDetailsInteractorImpl(
            MoviesLocalDataSourceImpl(JsonLoader(this)),
            MoviesRemoteDataSourceImpl(MoviesNetworkModule(BuildConfig.BASE_URL).moviesApi()),
            BuildConfig.BASE_IMG_URL
        )
    }
}

fun Context.appComponent() = (applicationContext as MyApp)

fun Fragment.appComponent() = requireContext().appComponent()