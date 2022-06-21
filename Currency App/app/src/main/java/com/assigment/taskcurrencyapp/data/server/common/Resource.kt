package com.assigment.taskcurrencyapp.data.server.common

sealed class Resource<T>(open val data:T? = null, open val message:String? = null){

    data class Success<T>(override val data: T?):Resource<T>(data)
    data class Loading<T>(val isLoading: Boolean):Resource<T>()
    data class Error<T>(override val data: T?, override val message: String?):Resource<T>(data, message)
}
