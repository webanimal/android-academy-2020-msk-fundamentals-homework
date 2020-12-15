package ru.webanimal.academy.fundamentals.homework.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.webanimal.academy.fundamentals.homework.data.models.Actor
import ru.webanimal.academy.fundamentals.homework.data.models.Genre
import ru.webanimal.academy.fundamentals.homework.data.models.Movie

private val jsonFormat = Json { ignoreUnknownKeys = true }

class JsonLoader(val context: Context) {
    suspend fun loadMoviesAsync(): List<Movie> = withContext(Dispatchers.IO) {
        val genresMap = loadGenres(context)
        val actorsMap = loadActors(context)

        val data = readAssetFileToString(context, "movies.json")
        parseMovies(data, genresMap, actorsMap)
    }
}

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

private fun parseGenres(data: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun parseActors(data: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { Actor(movieId = it.id, name = it.name, image = it.profilePicture) }
}

private fun parseMovies(
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): List<Movie> {

    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.movieId }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            posterList = jsonMovie.posterPicture,
            posterDetails = jsonMovie.backdropPicture,
            rating = normalizedRating(jsonMovie.ratings),
            reviewsCounter = jsonMovie.votesCount,
            allowedAge = normalizedAllowedAge(jsonMovie.adult),
            duration = jsonMovie.runtime,
            genres = normalizedGenres(genresMap, jsonMovie.genreIds),
            actors = normalizedActors(actorsMap, jsonMovie.actors)
        )
    }
}

private fun normalizedRating(rating: Float?): Int {
    return ((rating ?: 1f) / 2).toInt()
}

private fun normalizedAllowedAge(isAdult: Boolean): String {
    return if (isAdult) {
        16.toString()
    } else {
        13.toString()
    }
}

private fun normalizedActors(actorsMap: Map<Int, Actor>, actorIds: List<Int>): List<Actor> {
    return actorIds.mapNotNull { actorsMap[it] }
}

private fun normalizedGenres(genresMap: Map<Int, Genre>, genresIds: List<Int>): String {
    val sb = StringBuilder()
    genresIds.mapNotNull { genresMap[it] }
        .forEach { sb.append("${it.name}, ") }

    return if (sb.isNotEmpty()) {
        sb.removeSuffix(", ").toString()

    } else {
        ""
    }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)