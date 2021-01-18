package ru.webanimal.academy.fundamentals.homework.data.network

import ru.webanimal.academy.fundamentals.homework.data.network.apis.MoviesApi

interface MoviesNetworkClient {
    fun moviesApi(): MoviesApi
}