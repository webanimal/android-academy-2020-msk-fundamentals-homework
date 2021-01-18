package ru.webanimal.academy.fundamentals.homework.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResultDto(
    @SerialName("page")
    val page: Int = 1,

    @SerialName("results")
    val movies: List<ListMovieDto> = emptyList(),

    @SerialName("total_results")
    val moviesSize: Int = 0,

    @SerialName("total_pages")
    val pagesSize: Int = 1
)