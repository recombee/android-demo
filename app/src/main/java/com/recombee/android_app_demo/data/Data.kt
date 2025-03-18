package com.recombee.android_app_demo.data

sealed class Data<T> {
    data class Success<T>(val data: T) : Data<T>()

    data class Error<T>(val error: Throwable) : Data<T>()
}
