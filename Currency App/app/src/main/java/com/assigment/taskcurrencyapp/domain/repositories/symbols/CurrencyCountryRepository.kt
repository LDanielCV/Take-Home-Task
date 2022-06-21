package com.assigment.taskcurrencyapp.domain.repositories.symbols

import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.data.server.common.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyCountryRepository {
    fun getSymbols():Flow<Resource<out Any?>>

    fun searchCountries(query:String):Flow<List<CurrencySymbols>>

    suspend fun saveListData(list:List<CurrencySymbols>)
}