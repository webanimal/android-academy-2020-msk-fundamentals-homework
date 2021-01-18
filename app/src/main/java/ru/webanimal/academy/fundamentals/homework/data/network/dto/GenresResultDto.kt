package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResultDto(
    @SerialName("cast")
    val genres: List<GenreDto> = emptyList()
)