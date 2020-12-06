package ru.webanimal.academy.fundamentals.homework.features.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.domain.movies.provideMovieDataSource

class MovieDetailsFragment : Fragment() {

    private var listener: MovieDetailsBackClickListener? = null
    private val dataSource = provideMovieDataSource()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.layMovieDetailsHeader)?.setOnClickListener {
            listener?.onMovieDeselected()
        }

        val movie = dataSource.getMovieById(arguments?.getInt(KEY_MOVIE_ID) ?: 0)
        movie?.let {
            view.findViewById<TextView>(R.id.tvMovieDetailsFilmName)?.text = it.nameTwoLine
            view.findViewById<TextView>(R.id.tvMovieDetailsFilmGenre)?.text = it.genre
            view.findViewById<TextView>(R.id.tvMovieDetailsStorylineText)?.text = it.storyline
            view.findViewById<TextView>(R.id.tvMovieDetailsAllowedAge)?.text = it.allowedAge
            view.findViewById<TextView>(R.id.tvMovieDetailsReviewsCounter)?.text = it.reviewsCounter.toString()
            view.findViewById<ImageView>(R.id.ivMovieDetailsHeaderImage)?.setImageResource(it.bigPosterId)
            val rating = listOf<ImageView>(
                view.findViewById(R.id.ivMovieDetailsRatingStar1),
                view.findViewById(R.id.ivMovieDetailsRatingStar2),
                view.findViewById(R.id.ivMovieDetailsRatingStar3),
                view.findViewById(R.id.ivMovieDetailsRatingStar4),
                view.findViewById(R.id.ivMovieDetailsRatingStar5)
            )
            for (i in 0 until it.rating) {
                rating[i].setImageResource(R.drawable.ic_star_selected)
            }
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
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