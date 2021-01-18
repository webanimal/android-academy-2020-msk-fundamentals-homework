package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsMovieDto(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("imdb_id")
    val imdbId: String = "",

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

    @SerialName("runtime")
    val duration: Int = 0,

    @SerialName("budget")
    val budget: Int = 0,

    @SerialName("revenue")
    val revenue: Int = 0,

    @SerialName("status")
    val status: String = "Released",

    @SerialName("genres")
    val genres: List<GenreDto> = emptyList(),

    @SerialName("homepage")
    val homepage: String = "",

    @SerialName("backdrop_path")
    val posterDetails: String = "",
)