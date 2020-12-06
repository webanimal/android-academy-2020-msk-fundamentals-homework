package ru.webanimal.academy.fundamentals.homework.domain.movies

import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Actor
import ru.webanimal.academy.fundamentals.homework.data.models.Movie

fun provideMovieDataSource(): MovieDataSource = MovieDataSourceImpl()

interface MovieDataSource {
    fun getMovies(): List<Movie>
    fun getMovieById(movieId: Int): Movie?
    fun getActors(movieId: Int): List<Actor>?
}

private class MovieDataSourceImpl : MovieDataSource {

    override fun getMovies(): List<Movie> = movies

    override fun getMovieById(movieId: Int): Movie? = movies.find { movieId == it.id }

    override fun getActors(movieId: Int): List<Actor> = actors.filter { movieId == it.movieId }

    companion object {
        private val actors = listOf(
                Actor(0, "Robert Downey Jr.", R.drawable.img_avengers_actor_robert_downey_jr),
                Actor(0,"Chris Evans", R.drawable.img_avengers_actor_chris_evans),
                Actor(0,"Mark Ruffalo", R.drawable.img_avengers_actor_mark_ruffalo),
                Actor(0, "Chris Hemsworth", R.drawable.img_avengers_actor_chris_hemsworth),
                Actor(1, "John David Washington", R.drawable.img_tenet_actor_john_david_washington),
                Actor(1, "Elizabeth Debicki", R.drawable.img_tenet_actor_elizabeth_debicki),
                Actor(1, "Kenneth Branagh", R.drawable.img_tenet_actor_kenneth_branagh),
                Actor(1, "Michael Caine", R.drawable.img_tenet_actor_michael_caine),
                Actor(2, "Scarlett Johansson", R.drawable.img_widow_actor_scarlett_johansson),
                Actor(2, "Florence Pugh", R.drawable.img_widow_actor_florence_pugh),
                Actor(2, "David Harbour", R.drawable.img_widow_actor_david_harbour),
                Actor(2, "O.T. Fagbenle", R.drawable.img_widow_actor_o_t_fagbenle),
                Actor(3, "Gal Gadot", R.drawable.img_ww84_actor_gal_gadot),
                Actor(3, "Chris Pine", R.drawable.img_ww84_actor_chris_pine),
                Actor(3, "Kristen Wiig", R.drawable.img_ww84_actor_kristen_wiig),
                Actor(3, "Pedro Pascal", R.drawable.img_ww84_actor_pedro_pascal)
        )

        private val movies = listOf(
                Movie(
                        id = 0,
                        nameTwoLine = "Avengers:\nEnd Game",
                        name = "Avengers: End Game",
                        genre = "Action, Adventure, Fantasy",
                        storyline = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
                        allowedAge = "13+",
                        rating = 4,
                        reviewsCounter = 125,
                        duration = 137,
                        bigPosterId = R.drawable.img_movie_details_header_avengers,
                        smallPosterId = R.drawable.img_movies_item_header_avengers,
                        isFavorite = false,
                        actors = actors.filter { it.movieId == 0 }.toList()
                ),
                Movie(
                        id = 1,
                        nameTwoLine = "Tenet",
                        name = "Tenet",
                        genre = "Action, Sci-Fi, Thriller",
                        storyline = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
                        allowedAge = "16+",
                        rating = 5,
                        reviewsCounter = 98,
                        duration = 97,
                        bigPosterId = R.drawable.img_movie_details_header_tenet,
                        smallPosterId = R.drawable.img_movies_item_header_tenet,
                        isFavorite = true,
                        actors = actors.filter { it.movieId == 1 }.toList()
                ),
                Movie(
                        id = 2,
                        nameTwoLine = "Black\nWidow",
                        name = "Black Widow",
                        genre = "Action, Adventure, Sci-Fi",
                        storyline = "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                        allowedAge = "13+",
                        rating = 4,
                        reviewsCounter = 38,
                        duration = 102,
                        bigPosterId = R.drawable.img_movie_details_header_widow,
                        smallPosterId = R.drawable.img_movies_item_header_widow,
                        isFavorite = true,
                        actors = actors.filter { it.movieId == 2 }.toList()
                ),
                Movie(
                        id = 3,
                        nameTwoLine = "Wonder\nWoman 1984",
                        name = "Wonder Woman 1984",
                        genre = "Action, Adventure, Fantasy",
                        storyline = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                        allowedAge = "13+",
                        rating = 5,
                        reviewsCounter = 74,
                        duration = 120,
                        bigPosterId = R.drawable.img_movie_details_header_ww84,
                        smallPosterId = R.drawable.img_movies_item_header_ww84,
                        isFavorite = false,
                        actors = actors.filter { it.movieId == 3 }.toList()
                )
        )
    }
}