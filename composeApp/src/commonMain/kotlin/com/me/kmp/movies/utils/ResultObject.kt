package com.me.kmp.movies.utils

sealed class ResultObject<T> {
    data class Success<T>(
        val data: T,
    ) : ResultObject<T>()

    data class Error<T>(
        val exception: Exception,
    ) : ResultObject<T>()

    class Loading<T> : ResultObject<T>()

    class Empty<T> : ResultObject<T>()

    companion object {
        fun <T> success(data: T): ResultObject<T> = Success(data)

        fun <T> error(exception: Exception): ResultObject<T> = Error(exception)

        fun <T> loading(): ResultObject<T> = Loading()

        fun <T> empty(): ResultObject<T> = Empty()
    }
}

