package ru.webanimal.academy.fundamentals.homework.presentation.movies

import androidx.recyclerview.widget.DiffUtil
import ru.webanimal.academy.fundamentals.homework.domain.movies.models.ListMovie

class MoviesDiffCallback : DiffUtil.Callback() {

    private var oldList: List<ListMovie> = mutableListOf()
    private var newList: List<ListMovie> = mutableListOf()

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.isFavorite == newItem.isFavorite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    fun onNewList(oldList: List<ListMovie>, newList: List<ListMovie>): MoviesDiffCallback {
        this.oldList = oldList
        this.newList = newList

        return this
    }
}