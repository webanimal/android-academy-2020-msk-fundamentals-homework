package ru.webanimal.academy.fundamentals.homework

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.webanimal.academy.fundamentals.homework.data.JsonLoader
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSourceImpl

class MyApp : Application(), AppComponent {

    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        viewModelFactory = ViewModelFactory(MoviesDataSourceImpl(JsonLoader(this)))
    }

    override fun viewModelFactory(): ViewModelFactory = viewModelFactory
}

fun Context.appComponent() = (applicationContext as MyApp)

fun Fragment.appComponent() = requireContext().appComponent()