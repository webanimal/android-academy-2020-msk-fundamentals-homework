package ru.webanimal.academy.fundamentals.homework.domain.movies.models

data class DetailsMovie(
    val id: Int = 0,
    val imdbId: String = "",
    val title: String = "",
    val overview: String = "",
    val allowedAge: String = "",
    val rating: Int = 0,
    val reviewsCounter: Int = 0,
    val popularity: Float = 0.00f,
    val releaseDate: String = "",
    val duration: Int = 0,
    val budget: Int = 0,
    val revenue: Int = 0,
    val status: String = "Released",
    val genres: String = "",
    val isFavorite: Boolean = false,
    val homepage: String = "",
    val posterDetails: String = "",
    val actors: List<Actor> = emptyList()
)