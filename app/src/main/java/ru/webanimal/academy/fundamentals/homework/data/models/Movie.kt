package ru.webanimal.academy.fundamentals.homework.data.models

data class Movie(
        var id: Int,
        var title: String,
        val genres: String,
        var overview: String,
        var allowedAge: String,
        val posterList: String,
        val posterDetails: String,
        var rating: Int,
        var reviewsCounter: Int,
        var duration: Int,
        var isFavorite: Boolean = false,
        var actors: List<Actor>
)