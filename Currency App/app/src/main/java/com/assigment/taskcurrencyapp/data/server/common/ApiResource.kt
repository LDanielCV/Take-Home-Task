package com.assigment.taskcurrencyapp.data.server.common

import okhttp3.ResponseBody

sealed class ApiResource<out T>{
    data class Success<out T>(val value:T): ApiResource<T>()
    data class Failure(val errorCode:Int?, val errorBody:String?): ApiResource<Nothing>()
    data class NetWorkError(val errorCode: Int?, val errorBody: ResponseBody?):
        ApiResource<Nothing>()
}
