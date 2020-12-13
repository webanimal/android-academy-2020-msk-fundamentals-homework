package ru.webanimal.academy.fundamentals.homework.features.movies

import androidx.recyclerview.widget.DiffUtil
import ru.webanimal.academy.fundamentals.homework.data.models.Movie_legacy

class MoviesDiffCallback : DiffUtil.Callback() {
	
	private var oldList: List<Movie_legacy> = mutableListOf()
	private var newList: List<Movie_legacy> = mutableListOf()
	
	override fun getOldListSize(): Int = oldList.size
	
	override fun getNewListSize(): Int = newList.size
	
	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]
		return oldItem.id == newItem.id
				&& oldItem.name == newItem.name
				&& oldItem.isFavorite == newItem.isFavorite
	}
	
	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}
	
	fun onNewList(oldList: List<Movie_legacy>, newList: List<Movie_legacy>): MoviesDiffCallback {
		this.oldList = oldList
		this.newList = newList
		
		return this
	}
}