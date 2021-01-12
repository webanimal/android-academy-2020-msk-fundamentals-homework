package ru.webanimal.academy.fundamentals.homework.presentation.extensions

import android.view.View

fun View.visibleOrGone(setVisible: Boolean) {
    this.visibility = if (setVisible) {
        View.VISIBLE

    } else {
        View.GONE
    }
}