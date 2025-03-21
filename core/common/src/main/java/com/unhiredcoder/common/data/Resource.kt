package com.unhiredcoder.common.data

sealed class Resource<T>(val data: T?) {
    class Idle<T>(currentData: T? = null) : Resource<T>(currentData)
    class Loading<T>(currentData: T?) : Resource<T>(currentData)
    class Failure<T>(currentData: T?, val errorMessage: Throwable) : Resource<T>(currentData)
    class Success<T>(updatedData: T) : Resource<T>(updatedData)
}