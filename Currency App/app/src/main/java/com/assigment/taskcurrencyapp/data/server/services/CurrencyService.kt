package com.assigment.taskcurrencyapp.data.server.services

import com.assigment.taskcurrencyapp.BuildConfig.API_KEY
import com.assigment.taskcurrencyapp.domain.models.LatestRates
import com.assigment.taskcurrencyapp.domain.models.Symbols
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("symbols")
    suspend fun getSymbols(@Query("access_key") accessKey:String = API_KEY): Symbols
    @GET("latest")
    suspend fun getLatest( @Query("base")base:String, @Query("access_key") accessKey: String = API_KEY):LatestRates
}