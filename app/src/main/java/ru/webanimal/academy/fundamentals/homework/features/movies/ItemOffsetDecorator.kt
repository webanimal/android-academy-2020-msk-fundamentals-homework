package ru.webanimal.academy.fundamentals.homework.features.movies

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.webanimal.academy.fundamentals.homework.extensions.dpToPx

class ItemOffsetDecorator(
        private val appContext: Context,
        private val left: Float,
        private val top: Float,
        private val right: Float,
        private val bottom: Float
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(
            outRect.left + appContext.dpToPx(left).toInt(),
            outRect.top + appContext.dpToPx(top).toInt(),
            outRect.right + appContext.dpToPx(right).toInt(),
            appContext.dpToPx(bottom).toInt()
        )
    }
}