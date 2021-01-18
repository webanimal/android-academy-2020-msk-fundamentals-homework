package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResultDto(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("cast")
    val casts: List<CastDto> = emptyList()
)