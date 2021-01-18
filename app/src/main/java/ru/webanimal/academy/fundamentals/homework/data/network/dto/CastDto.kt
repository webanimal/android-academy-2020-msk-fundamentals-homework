package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("cast_id")
    val castId: Int = 0,

    @SerialName("credit_id")
    val creditId: String = "",

    @SerialName("id")
    val id: Int = 0,

    @SerialName("order")
    val orderInCredits: Int = 0,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("character")
    val character: String,

    @SerialName("known_for_department")
    val knownForDepartment: String,

    @SerialName("popularity")
    val popularity: Float = 0.0f,

    @SerialName("profile_path")
    val profileImgUrl: String? = "",
)