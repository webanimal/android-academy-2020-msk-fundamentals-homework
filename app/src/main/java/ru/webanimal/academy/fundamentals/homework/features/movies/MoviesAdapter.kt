package ru.webanimal.academy.fundamentals.homework.features.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Movie_legacy
import ru.webanimal.academy.fundamentals.homework.extensions.getString

class MoviesAdapter(
        private val listItemClickListener: MoviesListFragment.ListItemClickListener?,
        private val favoriteClickListener: MoviesListFragment.OnFavoriteClickListener
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val diffCallback = MoviesDiffCallback()
    private var moviesList: List<Movie_legacy> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when {
            moviesList.isNotEmpty() -> MOVIE_VIEW_HOLDER
            else -> EMPTY_VIEW_HOLDER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return when (viewType) {
            MOVIE_VIEW_HOLDER -> MoviesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_list, parent, false))
            EMPTY_VIEW_HOLDER -> EmptyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_empty, parent, false))
            else -> EmptyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_empty, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        when (holder) {
            is MoviesHolder -> {
                val movie = moviesList[position]
                holder.itemView.setOnClickListener { listItemClickListener?.onMovieSelected(movie.id) }
                holder.onBind(movie, favoriteClickListener)
            }
            is EmptyHolder -> { /* nothing to bind */ }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateAdapter(newMovieLegacies: List<Movie_legacy>?) {
        if (newMovieLegacies.isNullOrEmpty()) {
            return
        }
        
        DiffUtil.calculateDiff(
                diffCallback.onNewList(oldList = moviesList, newList = newMovieLegacies)
        ).dispatchUpdatesTo(this)
        moviesList = newMovieLegacies
    }

    abstract class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class EmptyHolder(itemView: View) : MoviesViewHolder(itemView)
    private class MoviesHolder(itemView: View) : MoviesViewHolder(itemView) {
        private val nameView: TextView? = itemView.findViewById(R.id.tvMoviesListFilmName)
        private val genreView: TextView? = itemView.findViewById(R.id.tvMoviesListFilmGenre)
        private val allowedAgeView: TextView? = itemView.findViewById(R.id.tvMoviesListAllowedAge)
        private val reviewsCounterView: TextView? = itemView.findViewById(R.id.tvMoviesListReviewsCounter)
        private val durationView: TextView? = itemView.findViewById(R.id.tvMoviesListFilmDuration)
        private val headerImage: ImageView? = itemView.findViewById(R.id.ivMoviesListHeaderImage)
        private val favoriteIcon: ImageView? = itemView.findViewById(R.id.ivMoviesListIsFavorite)
        private val ratingImages = listOf<ImageView?>(
            itemView.findViewById(R.id.ivMoviesListRatingStar1),
            itemView.findViewById(R.id.ivMoviesListRatingStar2),
            itemView.findViewById(R.id.ivMoviesListRatingStar3),
            itemView.findViewById(R.id.ivMoviesListRatingStar4),
            itemView.findViewById(R.id.ivMoviesListRatingStar5)
        )

        fun onBind(
            movieLegacy: Movie_legacy,
            favoriteClickListener: MoviesListFragment.OnFavoriteClickListener
        ) {
            
            nameView?.text = movieLegacy.name
            genreView?.text = movieLegacy.genre
            allowedAgeView?.text = movieLegacy.allowedAge
            reviewsCounterView?.text = movieLegacy.reviewsCounter.toString()
            durationView?.text = getString(R.string.movies_list_film_time, movieLegacy.duration.toString())
            headerImage?.setImageResource(movieLegacy.smallPosterId)
    
            val favoriteResId = if (movieLegacy.isFavorite) {
                R.drawable.ic_favorite_selected

            } else {
                R.drawable.ic_favorite_deselected
            }
            favoriteIcon?.setImageResource(favoriteResId)
            favoriteIcon?.setOnClickListener {
                favoriteClickListener.onClick(movieLegacy.copy(isFavorite = !movieLegacy.isFavorite))
            }

            var ratingResId: Int
            val ratingScore = movieLegacy.rating
            for (i in 0 until MAX_RATING_VALUE) {
                ratingResId = if (i < ratingScore) {
                    R.drawable.ic_star_selected

                } else {
                    R.drawable.ic_star_deselected
                }
                ratingImages[i]?.setImageResource(ratingResId)
            }
        }
    }
}

private const val MOVIE_VIEW_HOLDER = 0
private const val EMPTY_VIEW_HOLDER = 1
private const val MAX_RATING_VALUE = 5