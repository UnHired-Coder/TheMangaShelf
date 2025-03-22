package com.unhiredcoder.common.data

sealed class Resource<out T>(val data: T?) {
    class Idle<T>(currentData: T? = null) : Resource<T>(currentData)
    class Loading<T>(currentData: T?) : Resource<T>(currentData)
    class Failure<T>(currentData: T?, val errorMessage: Throwable) : Resource<T>(currentData)
    data class Success<T>(val updatedData: T) : Resource<T>(updatedData)
}