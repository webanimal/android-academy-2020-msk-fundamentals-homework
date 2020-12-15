package ru.webanimal.academy.fundamentals.homework

import android.app.Application
import ru.webanimal.academy.fundamentals.homework.data.JsonLoader
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSource
import ru.webanimal.academy.fundamentals.homework.domain.movies.MoviesDataSourceImpl

interface DataProvider {
	fun dataSource(): MoviesDataSource
}

class MyApp : Application(), DataProvider {
	
	private lateinit var moviesDataSource: MoviesDataSource
	private lateinit var moviesLoader: JsonLoader
	
	override fun onCreate() {
		super.onCreate()

		moviesLoader = JsonLoader(this)
		moviesDataSource = MoviesDataSourceImpl(moviesLoader)
	}
	
	override fun dataSource(): MoviesDataSource = moviesDataSource
}