package ru.webanimal.academy.fundamentals.homework.features.movies

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.webanimal.academy.fundamentals.homework.BaseFragment
import ru.webanimal.academy.fundamentals.homework.ItemOffsetDecorator
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Movie
import ru.webanimal.academy.fundamentals.homework.extensions.pxToDp

class MoviesListFragment : BaseFragment() {
    
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val isActive = coroutineScope.isActive
        Log.e(TAG, "ExceptionHandler [Scope active:$isActive, throwable:$throwable]")
        context?.let {
            Toast.makeText(it, "Load movies error", Toast.LENGTH_LONG).show()
        }
        coroutineScope = createCoroutineScope()
    }
    private var coroutineScope = createCoroutineScope()
    
    private var recycler: RecyclerView? = null
    private var listItemClickListener: ListItemClickListener? = null
    private var columnsValue = PORTRAIT_LIST_COLUMNS_COUNT
    private var actualListItemWidth = 0
    private var listMargins = 32
    private var movies: List<Movie> = arrayListOf()

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

        updateData()
    }

    override fun onDetach() {
        coroutineScope.cancel("It's time")

        recycler = null
        listItemClickListener = null

        super.onDetach()
    }
    
    private fun updateData() {
        coroutineScope.launch(coroutineExceptionHandler) {
            movies = dataProvider?.dataSource()?.getMoviesAsync(true) ?: emptyList()
            updateAdapter(movies)
        }
    }
    
    private fun updateFavorites(movie: Movie) {
        coroutineScope.launch(coroutineExceptionHandler) {
            dataProvider?.dataSource()?.updateMovieAsync(movie)
            movies = dataProvider?.dataSource()?.getMoviesAsync() ?: emptyList()
            updateAdapter(movies)
        }
    }
    
    private fun updateAdapter(movies: List<Movie>) {
        (recycler?.adapter as? MoviesAdapter)?.updateAdapter(movies)
    }

    private fun calculateListLayoutParams() {
        val screenWidth = resources.configuration.screenWidthDp
        val cardWidth = resources.getDimension(R.dimen.movies_list_card_width)
        val cardWidthPx = context?.pxToDp(cardWidth) ?: 170
        val outerMargins = listMargins
        val decorMargins = (ADAPTER_DECORATION_SPACE * 2).toInt()
        val availableSpace = screenWidth - outerMargins - cardWidthPx
        columnsValue = 1 + (availableSpace / (cardWidthPx + decorMargins))
        val additionalSpace = screenWidth - outerMargins - cardWidthPx * columnsValue - decorMargins * (columnsValue - 1)
        actualListItemWidth = cardWidth.toInt() + additionalSpace / columnsValue
        /*
        Log.d("TEST::", """
                screenWidth:$screenWidth
                cardWidth:$cardWidth
                cardWidthPx:$cardWidthPx
                outerMargins:$outerMargins
                decorMargins:$decorMargins
                availableSpace:$availableSpace
                columnsValue:$columnsValue
                additionalSpace:$additionalSpace
                actualListItemWidth:$actualListItemWidth
            """.trimIndent()
        )
        */
    }

    private fun setupViews(view: View) {
        recycler = view.findViewById<RecyclerView>(R.id.rvMovies).apply {
            layoutManager = GridLayoutManager(view.context, columnsValue)
            addItemDecoration(ItemOffsetDecorator(
                context.applicationContext,
                left = ADAPTER_DECORATION_SPACE,
                top = ADAPTER_DECORATION_SPACE,
                right = ADAPTER_DECORATION_SPACE,
                bottom = ADAPTER_DECORATION_SPACE
            ))
            adapter = MoviesAdapter(
                actualListItemWidth,
                listItemClickListener,
                object : OnFavoriteClickListener {
                    override fun onClick(movie: Movie) {
                        updateFavorites(movie)
                    }
                }
            )
        }
    }
    
    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.Main)

    interface ListItemClickListener {
        fun onMovieSelected(movieId: Int)
    }
    
    interface OnFavoriteClickListener {
        fun onClick(movie: Movie)
    }

    companion object {
        private val TAG = MoviesListFragment::class.java.simpleName
        
        fun create() = MoviesListFragment()
    }
}

private const val PORTRAIT_LIST_COLUMNS_COUNT = 2
private const val LANDSCAPE_LIST_COLUMNS_COUNT = 3
private const val ADAPTER_DECORATION_SPACE = 8f