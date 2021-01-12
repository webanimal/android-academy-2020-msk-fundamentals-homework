package ru.webanimal.academy.fundamentals.homework.domain.movies.models

data class Movie(
        var id: Int = 0,
        var title: String = "",
        val genres: String = "",
        var overview: String = "",
        var allowedAge: String = "",
        val posterList: String = "",
        val posterDetails: String = "",
        var rating: Int = 0,
        var reviewsCounter: Int = 0,
        var duration: Int = 0,
        var isFavorite: Boolean = false,
        var actors: List<Actor> = emptyList()
)