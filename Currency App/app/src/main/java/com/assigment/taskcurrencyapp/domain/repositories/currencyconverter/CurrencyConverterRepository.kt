package com.assigment.taskcurrencyapp.domain.repositories.currencyconverter


import com.assigment.taskcurrencyapp.domain.models.Rate
import com.assigment.taskcurrencyapp.data.server.common.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterRepository {

    fun getLatestRates(base:String): Flow<Resource<out Any?>>

    fun convertRates(valueToConvert:Float, base:String):Flow<List<Rate>>

   suspend fun saveDataList(list: List<Rate>)
}