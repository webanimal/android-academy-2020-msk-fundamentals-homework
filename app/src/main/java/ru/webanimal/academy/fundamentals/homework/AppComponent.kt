package ru.webanimal.academy.fundamentals.homework

import ru.webanimal.academy.fundamentals.homework.presentation.core.ViewModelFactory

interface AppComponent {
    fun viewModelFactory(): ViewModelFactory
}