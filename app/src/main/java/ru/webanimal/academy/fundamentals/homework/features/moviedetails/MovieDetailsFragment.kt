package ru.webanimal.academy.fundamentals.homework.features.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.webanimal.academy.fundamentals.homework.R

class MovieDetailsFragment : Fragment() {

    var listener: MovieDetailsBackClickListener? = null

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

    override fun onStart() {
        super.onStart()

        view?.findViewById<View>(R.id.layMovieDetailsHeader)?.setOnClickListener {
            listener?.onMovieDeselected()
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
        fun create() = MovieDetailsFragment()
    }
}