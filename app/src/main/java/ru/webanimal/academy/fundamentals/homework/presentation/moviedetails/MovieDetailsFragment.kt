package ru.webanimal.academy.fundamentals.homework.presentation.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.webanimal.academy.fundamentals.homework.BaseFragment
import ru.webanimal.academy.fundamentals.homework.ItemOffsetDecorator
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.appComponent
import ru.webanimal.academy.fundamentals.homework.data.models.Actor
import ru.webanimal.academy.fundamentals.homework.data.models.Movie
import ru.webanimal.academy.fundamentals.homework.extensions.visibleOrGone

class MovieDetailsFragment : BaseFragment() {

    private lateinit var viewModel: MovieDetailsViewModel

    private var actorsRecycler: RecyclerView? = null
    private var filmNameView: TextView? = null
    private var genreView: TextView? = null
    private var storylineView: TextView? = null
    private var allowedAgeView: TextView? = null
    private var reviewsCounterView: TextView? = null
    private var castsView: TextView? = null
    private var posterImage: ImageView? = null
    private var ratings: List<ImageView> = emptyList()

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

        setupViews(parent)
        setupListeners(parent)
        setupViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MOVIE_ID, movieId)
    }

    override fun onDetach() {
        clearViews()
        backClickListener = null

        super.onDetach()
    }

    private fun updateActors(actors: List<Actor>) {
        val isNotEmpty = actors.isNotEmpty()
        if (isNotEmpty) {
            (actorsRecycler?.adapter as? ActorsAdapter)?.updateAdapter(actors)
        }
        updateActorsVisibility(setVisible = isNotEmpty)
    }

    private fun bindViews(movie: Movie) {
        filmNameView?.text = movie.title
        genreView?.text = movie.genres
        storylineView?.text = movie.overview
        allowedAgeView?.text = movie.allowedAge
        reviewsCounterView?.text = movie.reviewsCounter.toString()

        posterImage?.let { posterView ->
            Picasso.get().load(movie.posterDetails)
                .placeholder(R.drawable.img_coming_soon_placeholder)
                .into(posterView)
        }

        for (i in 0 until movie.rating) {
            ratings[i].setImageResource(R.drawable.ic_star_selected)
        }
    }

    private fun setupViews(parent: View) {
        filmNameView = parent.findViewById(R.id.tvMovieDetailsFilmName)
        genreView = parent.findViewById(R.id.tvMovieDetailsFilmGenre)
        storylineView = parent.findViewById(R.id.tvMovieDetailsStorylineText)
        allowedAgeView = parent.findViewById(R.id.tvMovieDetailsAllowedAge)
        reviewsCounterView = parent.findViewById(R.id.tvMovieDetailsReviewsCounter)
        castsView = parent.findViewById(R.id.tvMovieDetailsCastTitle)
        posterImage = parent.findViewById(R.id.ivMovieDetailsHeaderImage)
        ratings = listOf(
            parent.findViewById(R.id.ivMovieDetailsRatingStar1),
            parent.findViewById(R.id.ivMovieDetailsRatingStar2),
            parent.findViewById(R.id.ivMovieDetailsRatingStar3),
            parent.findViewById(R.id.ivMovieDetailsRatingStar4),
            parent.findViewById(R.id.ivMovieDetailsRatingStar5)
        )

        actorsRecycler = parent.findViewById<RecyclerView>(R.id.rvMovieDetailsActors)?.apply {
            addItemDecoration(
                ItemOffsetDecorator(
                    context.applicationContext,
                    left = ADAPTER_DECORATION_SPACE,
                    top = ADAPTER_DECORATION_SPACE,
                    right = ADAPTER_DECORATION_SPACE,
                    bottom = ADAPTER_DECORATION_SPACE
                )
            )
            adapter = ActorsAdapter()
        }
        updateActorsVisibility(setVisible = false)
    }

    private fun clearViews() {
        actorsRecycler = null
        filmNameView = null
        genreView = null
        storylineView = null
        allowedAgeView = null
        reviewsCounterView = null
        posterImage = null
        ratings = emptyList()
    }

    private fun updateActorsVisibility(setVisible: Boolean) {
        castsView?.visibleOrGone(setVisible)
        actorsRecycler?.visibleOrGone(setVisible)
    }

    private fun setupListeners(parent: View) {
        parent.findViewById<View>(R.id.layMovieDetailsHeader)?.setOnClickListener {
            backClickListener?.onMovieDeselected()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, appComponent().viewModelFactory())
            .get(MovieDetailsViewModel::class.java)
        viewModel.movie.observe(this.viewLifecycleOwner) {
            bindViews(it)
            updateActors(it.actors)
        }
        if (movieId > 0) {
            viewModel.onViewCreated(movieId)
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
        private val TAG = MovieDetailsFragment::class.java.simpleName

        fun create(movieId: Int) = MovieDetailsFragment().apply {
            arguments = Bundle().apply { putInt(KEY_MOVIE_ID, movieId) }
        }
    }
}

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
private const val ADAPTER_DECORATION_SPACE = 8f