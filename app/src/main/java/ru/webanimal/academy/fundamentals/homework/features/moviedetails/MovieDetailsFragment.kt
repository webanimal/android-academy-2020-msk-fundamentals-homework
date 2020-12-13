package ru.webanimal.academy.fundamentals.homework.features.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.webanimal.academy.fundamentals.homework.ItemOffsetDecorator
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Movie_legacy
import ru.webanimal.academy.fundamentals.homework.domain.movies.provideMoviesDataSource
import ru.webanimal.academy.fundamentals.homework.extensions.visibleOrGone

class MovieDetailsFragment : Fragment() {

    private val dataSource = provideMoviesDataSource()
    private var actorsRecycler: RecyclerView? = null
    private var backClickListener: MovieDetailsBackClickListener? = null
    private var movieId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            backClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(parent: View, savedInstanceState: Bundle?) {
        super.onViewCreated(parent, savedInstanceState)

        movieId = extractMovieId(args = arguments, savedState = savedInstanceState)
        val movie = dataSource.getMovieById(movieId)
        setupViews(parent, movie)

        setupListeners(parent)
    }

    override fun onStart() {
        super.onStart()

        dataSource.getActors(movieId)?.let {
            (actorsRecycler?.adapter as? ActorsAdapter)?.updateAdapter(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MOVIE_ID, movieId)
    }

    override fun onDetach() {
        actorsRecycler = null
        backClickListener = null

        super.onDetach()
    }

    private fun setupViews(parent: View, movieLegacy: Movie_legacy?) {
        movieLegacy?.let {
            parent.findViewById<TextView>(R.id.tvMovieDetailsFilmName)?.text = it.nameTwoLine
            parent.findViewById<TextView>(R.id.tvMovieDetailsFilmGenre)?.text = it.genre
            parent.findViewById<TextView>(R.id.tvMovieDetailsStorylineText)?.text = it.storyline
            parent.findViewById<TextView>(R.id.tvMovieDetailsAllowedAge)?.text = it.allowedAge
            parent.findViewById<TextView>(R.id.tvMovieDetailsReviewsCounter)?.text = it.reviewsCounter.toString()
            parent.findViewById<ImageView>(R.id.ivMovieDetailsHeaderImage)?.setImageResource(it.bigPosterId)
            val rating = listOf<ImageView>(
                    parent.findViewById(R.id.ivMovieDetailsRatingStar1),
                    parent.findViewById(R.id.ivMovieDetailsRatingStar2),
                    parent.findViewById(R.id.ivMovieDetailsRatingStar3),
                    parent.findViewById(R.id.ivMovieDetailsRatingStar4),
                    parent.findViewById(R.id.ivMovieDetailsRatingStar5)
            )
            for (i in 0 until it.rating) {
                rating[i].setImageResource(R.drawable.ic_star_selected)
            }
        }

        actorsRecycler = parent.findViewById<RecyclerView>(R.id.rvMovieDetailsActors)?.apply {
            addItemDecoration(ItemOffsetDecorator(
                    context.applicationContext,
                    left = ADAPTER_DECORATION_SPACE,
                    top = ADAPTER_DECORATION_SPACE,
                    right = ADAPTER_DECORATION_SPACE,
                    bottom = ADAPTER_DECORATION_SPACE
            ))
            adapter = ActorsAdapter()
            visibleOrGone(setVisible = movieLegacy != null)
        }
    }

    private fun setupListeners(parent: View) {
        parent.findViewById<View>(R.id.layMovieDetailsHeader)?.setOnClickListener {
            backClickListener?.onMovieDeselected()
        }
    }

    private fun extractMovieId(args: Bundle?, savedState: Bundle?): Int {
        var id = args?.getInt(KEY_MOVIE_ID, -1) ?: -1
        if (id < 0) {
            id = savedState?.getInt(KEY_MOVIE_ID, 0) ?: 0
        }

        return id
    }

    interface MovieDetailsBackClickListener {
        fun onMovieDeselected()
    }

    companion object {
        fun create(movieId: Int) = MovieDetailsFragment().apply {
            arguments = Bundle().apply { putInt(KEY_MOVIE_ID, movieId) }
        }
    }
}

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
private const val ADAPTER_DECORATION_SPACE = 8f