package ru.webanimal.academy.fundamentals.homework.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    this.resources.displayMetrics
)