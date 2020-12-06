package ru.webanimal.academy.fundamentals.homework.data.models

data class Movie(
        var id: Int,
        var nameTwoLine: String,
        var name: String,
        var genre: String,
        var storyline: String,
        var allowedAge: String,
        var bigPosterId: Int,
        var smallPosterId: Int,
        var rating: Int,
        var reviewsCounter: Int,
        var duration: Int,
        var isFavorite: Boolean,
        var actors: List<Actor>
)
