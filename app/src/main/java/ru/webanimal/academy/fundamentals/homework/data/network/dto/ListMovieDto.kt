package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListMovieDto(
        @SerialName("id")
        val id: Int = 0,

        @SerialName("title")
        val title: String = "",

        @SerialName("overview")
        val overview: String = "",

        @SerialName("adult")
        val isAdult: Boolean = false,

        @SerialName("vote_average")
        val rating: Float = 0.0f,

        @SerialName("vote_count")
        val reviewsCounter: Int = 0,

        @SerialName("popularity")
        val popularity: Float = 0.0f,

        @SerialName("release_date")
        val releaseDate: String = "",

        @SerialName("genre_ids")
        val genres: List<Int> = emptyList(),

        @SerialName("poster_path")
        val posterList: String = "",

        @SerialName("backdrop_path")
        val posterDetails: String = "",
)