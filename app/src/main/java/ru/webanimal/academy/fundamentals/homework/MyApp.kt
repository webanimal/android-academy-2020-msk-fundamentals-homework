package ru.webanimal.academy.fundamentals.homework

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.webanimal.academy.fundamentals.homework.data.network.MoviesNetworkModule
import ru.webanimal.academy.fundamentals.homework.data.storage.JsonLoader
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesInteractor
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesInteractorImpl
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesLocalDataSourceImpl
import ru.webanimal.academy.fundamentals.homework.domain.movies.datasources.MoviesRemoteDataSourceImpl
import ru.webanimal.academy.fundamentals.homework.presentation.core.ViewModelFactory

class MyApp : Application(), AppComponent {

    private lateinit var moviesInteractor: MoviesInteractor
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        initInteractors()
        viewModelFactory = ViewModelFactory(moviesInteractor)
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory

    private fun initInteractors() {
        moviesInteractor = MoviesInteractorImpl(
            MoviesLocalDataSourceImpl(JsonLoader(this)),
            MoviesRemoteDataSourceImpl(MoviesNetworkModule(BuildConfig.BASE_URL).moviesApi())
        )
    }
}

fun Context.appComponent() = (applicationContext as MyApp)

fun Fragment.appComponent() = requireContext().appComponent()