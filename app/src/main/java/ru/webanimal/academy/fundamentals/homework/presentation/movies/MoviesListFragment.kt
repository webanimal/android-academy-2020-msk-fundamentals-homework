package ru.webanimal.academy.fundamentals.homework.presentation.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.webanimal.academy.fundamentals.homework.*
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.ListMovie
import ru.webanimal.academy.fundamentals.homework.presentation.core.BaseFragment
import ru.webanimal.academy.fundamentals.homework.presentation.ItemOffsetDecorator
import ru.webanimal.academy.fundamentals.homework.presentation.extensions.pxToDp

class MoviesListFragment : BaseFragment() {

    private lateinit var viewModel: MoviesListViewModel

    private var recycler: RecyclerView? = null
    private var listItemClickListener: ListItemClickListener? = null
    private var columnsValue = 0
    private var actualListItemWidth = 0
    private var listMargins = 32

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ListItemClickListener) {
            listItemClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculateListLayoutParams()
        setupViews(view)
        setupViewModel()
    }

    override fun onDestroyView() {
        clearViews()

        super.onDestroyView()
    }

    override fun onDetach() {
        listItemClickListener = null

        super.onDetach()
    }
    
    private fun updateAdapter(movies: List<ListMovie>) {
        (recycler?.adapter as? MoviesAdapter)?.updateAdapter(movies)
    }

    private fun calculateListLayoutParams() {
        val screenWidth = resources.configuration.screenWidthDp
        val cardWidth = resources.getDimension(R.dimen.movies_list_card_width)
        val cardWidthPx = context?.pxToDp(cardWidth) ?: ADAPTER_LIST_ITEM_DEFAULT_WIDTH
        val outerMargins = listMargins
        val decorMargins = (ADAPTER_DECORATION_SPACE * 2).toInt()
        val availableSpace = screenWidth - outerMargins - cardWidthPx
        columnsValue = 1 + (availableSpace / (cardWidthPx + decorMargins))
        val additionalSpace = screenWidth - outerMargins - cardWidthPx * columnsValue - decorMargins * (columnsValue - 1)
        actualListItemWidth = cardWidth.toInt() + additionalSpace / columnsValue
    }

    private fun setupViews(view: View) {
        recycler = view.findViewById<RecyclerView>(R.id.rvMovies).apply {
            layoutManager = GridLayoutManager(view.context, columnsValue)
            addItemDecoration(
                ItemOffsetDecorator(
                context.applicationContext,
                left = ADAPTER_DECORATION_SPACE,
                top = ADAPTER_DECORATION_SPACE,
                right = ADAPTER_DECORATION_SPACE,
                bottom = ADAPTER_DECORATION_SPACE
            )
            )
            adapter = MoviesAdapter(
                actualListItemWidth,
                listItemClickListener,
                object : OnFavoriteClickListener {
                    override fun onClick(movie: ListMovie) {
                        viewModel.onFavoriteClick(movie)
                    }
                }
            )
        }
    }

    private fun clearViews() {
        recycler = null
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, appComponent().viewModelFactory())
            .get(MoviesListViewModel::class.java)
        viewModel.movies.observe(this.viewLifecycleOwner) {
            updateAdapter(it)
        }
        viewModel.onViewCreated()
    }
    
    interface ListItemClickListener {
        fun onMovieSelected(movieId: Int)
    }
    
    interface OnFavoriteClickListener {
        fun onClick(movie: ListMovie)
    }

    companion object {
        private val TAG = MoviesListFragment::class.java.simpleName
        
        fun create() = MoviesListFragment()
    }
}

private const val ADAPTER_DECORATION_SPACE = 8f
private const val ADAPTER_LIST_ITEM_DEFAULT_WIDTH = 170