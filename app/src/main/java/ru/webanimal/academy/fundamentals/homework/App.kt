package ru.webanimal.academy.fundamentals.homework

import android.app.Application
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.provideMoviesDataSource

interface DataProvider {
	fun getDataSource(): MoviesDataSource
}

class App : Application(), DataProvider {
	
	private val moviesDataSource = provideMoviesDataSource()
	
	override fun getDataSource(): MoviesDataSource = moviesDataSource
}