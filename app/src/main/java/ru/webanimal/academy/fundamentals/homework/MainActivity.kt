package ru.webanimal.academy.fundamentals.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.webanimal.academy.fundamentals.homework.features.moviedetails.MovieDetailsFragment
import ru.webanimal.academy.fundamentals.homework.features.movies.MoviesListFragment

class MainActivity : AppCompatActivity(),
    MoviesListFragment.ListItemClickListener,
    MovieDetailsFragment.MovieDetailsBackClickListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToMoviesList()
        }
    }

    override fun onMovieSelected(movieId: Int) {
        routeToMovieDetails(movieId)
    }

    override fun onMovieDeselected() {
        routeBack()
    }

    private fun routeToMoviesList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MoviesListFragment.create(), MoviesListFragment::class.java.simpleName)
            .addToBackStack("trans:${MoviesListFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeToMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, MovieDetailsFragment.create(movieId), MovieDetailsFragment::class.java.simpleName)
            .addToBackStack("trans:${MovieDetailsFragment::class.java.simpleName}")
            .commit()
    }

    private fun routeBack() {
        supportFragmentManager.popBackStack()
    }
}