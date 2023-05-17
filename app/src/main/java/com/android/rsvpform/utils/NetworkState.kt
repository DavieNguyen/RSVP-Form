package com.android.rsvpform.utils

sealed class NetworkState<out T> {
    data class Success<out T>(val message: T) : NetworkState<T>()
    data class Error<T>(val errorMessage: String) : NetworkState<T>()
}
