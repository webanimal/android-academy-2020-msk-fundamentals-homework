package ru.webanimal.academy.fundamentals.homework.domain.movies.models

data class Actor(
    val movieId: Int,
    val castId: Int,
    val orderInCredits: Int,
    val name: String,
    val character: String,
    val imageUrl: String
)
