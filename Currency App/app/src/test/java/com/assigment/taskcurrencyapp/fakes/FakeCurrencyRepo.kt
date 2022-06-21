package com.assigment.taskcurrencyapp.fakes

import com.assigment.taskcurrencyapp.domain.models.Rate
import com.assigment.taskcurrencyapp.domain.repositories.currencyconverter.CurrencyConverterRepository
import com.assigment.taskcurrencyapp.data.server.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCurrencyRepo: CurrencyConverterRepository{

    init {
        latestRates.succes = true
    }

    override fun getLatestRates(base: String): Flow<Resource<out Any?>> = flowOf(
        when(searchMap(base).succes) {
            true ->
                Resource.Success (rates)

            else ->
                Resource.Error(data = null, "Failure")
        }
    )


    override fun convertRates(valueToConvert: Float, base: String): Flow<List<Rate>> = flowOf(rates.map { rate ->
        rate.value *= valueToConvert
        rate
    })

    override suspend fun saveDataList(list: List<Rate>) {
        TODO("Not yet implemented")
    }

}

