package ru.webanimal.academy.fundamentals.homework.data.network.dto

data class MovieDto(
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
        var actorEntities: List<ActorDto> = emptyList()
)