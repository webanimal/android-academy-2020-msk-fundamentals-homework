package ru.webanimal.academy.fundamentals.homework.domain.movies.models

data class ListMovie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val allowedAge: String = "",
    val rating: Int = 0,
    val reviewsCounter: Int = 0,
    val popularity: Float = 0.00f,
    val releaseDate: String = "",
    val genres: String = "",
    val isFavorite: Boolean = false,
    val posterList: String = ""
)