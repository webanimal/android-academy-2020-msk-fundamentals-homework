package ru.webanimal.academy.fundamentals.homework.presentation.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    this.resources.displayMetrics
)

fun Context.pxToDp(px: Float): Int {
    val metrics = this.resources.displayMetrics
    return (px / metrics.density).toInt()
}
