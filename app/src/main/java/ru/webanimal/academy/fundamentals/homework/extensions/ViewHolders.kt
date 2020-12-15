package ru.webanimal.academy.fundamentals.homework.extensions

import androidx.recyclerview.widget.RecyclerView

val RecyclerView.ViewHolder.context
    get() = this.itemView.context

fun RecyclerView.ViewHolder.getString(resId: Int): String = itemView.context.getString(resId)
fun RecyclerView.ViewHolder.getString(resId: Int, arg: String): String = itemView.context.getString(resId, arg)