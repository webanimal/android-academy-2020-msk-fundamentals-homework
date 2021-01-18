package ru.webanimal.academy.fundamentals.homework.data.storage

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.webanimal.academy.fundamentals.homework.data.storage.entities.ActorEntity
import ru.webanimal.academy.fundamentals.homework.data.storage.entities.GenreEntity
import ru.webanimal.academy.fundamentals.homework.data.storage.entities.MovieEntity

private val jsonFormat = Json { ignoreUnknownKeys = true }

class JsonLoader(val context: Context) {
    suspend fun loadMoviesAsync(): List<MovieEntity> = withContext(Dispatchers.IO) {
        val genresMap = loadGenres(context)
        val actorsMap = loadActors(context)

        val data = readAssetFileToString(context, "movies.json")
        parseMovies(data, genresMap, actorsMap)
    }
}

private suspend fun loadGenres(context: Context): List<GenreEntity> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

private suspend fun loadActors(context: Context): List<ActorEntity> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

private fun parseGenres(data: String): List<GenreEntity> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { GenreEntity(id = it.id, name = it.name) }
}

private fun parseActors(data: String): List<ActorEntity> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { ActorEntity(movieId = it.id, name = it.name, image = it.profilePicture) }
}

private fun parseMovies(
    data: String,
    genreEntities: List<GenreEntity>,
    actorEntities: List<ActorEntity>
): List<MovieEntity> {

    val genresMap = genreEntities.associateBy { it.id }
    val actorsMap = actorEntities.associateBy { it.movieId }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        MovieEntity(
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
            actorEntities = normalizedActors(actorsMap, jsonMovie.actors)
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

private fun normalizedActors(actorsMap: Map<Int, ActorEntity>, actorIds: List<Int>): List<ActorEntity> {
    return actorIds.mapNotNull { actorsMap[it] }
}

private fun normalizedGenres(genresMap: Map<Int, GenreEntity>, genresIds: List<Int>): String {
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